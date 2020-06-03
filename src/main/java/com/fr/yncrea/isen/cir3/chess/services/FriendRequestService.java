package com.fr.yncrea.isen.cir3.chess.services;

import com.fr.yncrea.isen.cir3.chess.domain.FriendRequest;
import com.fr.yncrea.isen.cir3.chess.form.FriendRequestForm;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    public FriendRequestForm createForm(FriendRequest req) {
        FriendRequestForm form = new FriendRequestForm();
        if (req == null) {
            return form;
        }

        form.setId(req.getId());
        form.setUsername(req.getReceiver().getUsername());
        form.setSender(req.getSender());
        return form;
    }
}
