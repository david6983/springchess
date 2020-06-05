package com.fr.yncrea.isen.cir3.chess.services;

import com.fr.yncrea.isen.cir3.chess.domain.FriendRequest;
import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.repository.FriendRequestRepository;
import com.fr.yncrea.isen.cir3.chess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendRequestService {
    @Autowired
    private UserRepository users;

    @Autowired
    private FriendRequestRepository friendRequests;

    public List<User> getFriendUserList(User receiver) {
        List<FriendRequest> friends = friendRequests.findAllByReceiverAndIsAccepted(receiver, true);
        List<User> userFriends = new ArrayList<>();

        for (FriendRequest friend: friends) {
            userFriends.add(users.findByUsername(friend.getSender()));
        }

        return userFriends;
    }
}
