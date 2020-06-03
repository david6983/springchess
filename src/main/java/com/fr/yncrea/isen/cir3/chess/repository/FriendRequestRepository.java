package com.fr.yncrea.isen.cir3.chess.repository;

import com.fr.yncrea.isen.cir3.chess.domain.FriendRequest;
import com.fr.yncrea.isen.cir3.chess.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findAllByReceiverAndIsAccepted(User receiver, Boolean isAccepted); // list of friends
    void deleteByReceiverAndSenderAndIsAccepted(User receiver, String sender, Boolean isAccepted);
    Optional<FriendRequest> findByReceiverAndSenderAndIsAccepted(User receiver, String sender, Boolean isAccepted);
}
