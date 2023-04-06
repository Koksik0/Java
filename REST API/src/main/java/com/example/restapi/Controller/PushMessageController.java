package com.example.restapi.Controller;

import com.example.restapi.Post;
import com.example.restapi.SenderException;
import com.example.restapi.Service.PushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PushMessageController {
  @Autowired
  PushMessageService pushMessageService;

  @RequestMapping(value = "/push")
  public String push(
      Model model,
      @RequestParam String address,
      @RequestParam String title,
      @RequestParam String message) {
    String attributeName = "sendPush";
    try {
      model.addAttribute(attributeName, pushMessageService.sendPush(address, title, message));
    } catch (SenderException senderException) {
      model.addAttribute(attributeName, senderException.getMessage());
    }
    return "push";
  }

  @PostMapping(value = "/push/post")
  public String pushEmail(@ModelAttribute("sendPush") Post post, Model model) {
    String attributeName = "sendPush";
    try {
      model.addAttribute(
          attributeName,
          pushMessageService.sendPush(post.getAddress(), post.getTitle(), post.getMessage()));
    } catch (SenderException senderException) {
      model.addAttribute(attributeName, senderException.getMessage());
    }
    return "push";
  }
}
