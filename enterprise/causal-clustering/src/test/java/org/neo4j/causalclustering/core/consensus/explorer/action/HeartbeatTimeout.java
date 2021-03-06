/*
 * Copyright (c) 2018-2020 "Graph Foundation"
 * Graph Foundation, Inc. [https://graphfoundation.org]
 *
 * Copyright (c) 2002-2018 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of ONgDB Enterprise Edition. The included source
 * code can be redistributed and/or modified under the terms of the
 * GNU AFFERO GENERAL PUBLIC LICENSE Version 3
 * (http://www.fsf.org/licensing/licenses/agpl-3.0.html) as found
 * in the associated LICENSE.txt file.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 */
package org.neo4j.causalclustering.core.consensus.explorer.action;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.neo4j.causalclustering.core.consensus.RaftMessages;
import org.neo4j.causalclustering.core.consensus.explorer.ClusterState;
import org.neo4j.causalclustering.identity.MemberId;

public class HeartbeatTimeout implements Action
{
    private final MemberId member;

    public HeartbeatTimeout( MemberId member )
    {
        this.member = member;
    }

    @Override
    public ClusterState advance( ClusterState previous )
    {
        ClusterState newClusterState = new ClusterState( previous );
        Queue<RaftMessages.RaftMessage> newQueue = new LinkedList<>( previous.queues.get( member ) );
        newQueue.offer( new RaftMessages.Timeout.Heartbeat( member ) );
        newClusterState.queues.put( member, newQueue );
        return newClusterState;
    }
}
