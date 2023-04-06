package com.example.restapi.Controller;

import com.example.restapi.Post;
import com.example.restapi.SenderException;
import com.example.restapi.Service.EmailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmailMassageController {
  @Autowired
  EmailMessageService emailMessageService;

  @RequestMapping(value = "/email")
  public void email(
      Model model,
      @RequestParam String address,
      @RequestParam String title,
      @RequestParam String message) {
    String attributeName = "sendEmail";

    try {
      model.addAttribute(attributeName, emailMessageService.sendEmail(address, title, message));
    } catch (SenderException senderException) {
      model.addAttribute(attributeName, senderException.getMessage());
    } catch (InterruptedException e) {
      model.addAttribute(attributeName, "InterruptedException");
    }
  }

  @PostMapping(value = "/email/post")
  public String emailPost(@ModelAttribute("sendEmail") Post post, Model model) {
    String attributeName = "sendEmail";
    try {
      model.addAttribute(
          attributeName,
          emailMessageService.sendEmail(post.getAddress(), post.getTitle(), post.getMessage()));
    } catch (SenderException senderException) {
      model.addAttribute(attributeName, senderException.getMessage());
    } catch (InterruptedException e) {
      model.addAttribute(attributeName, "InterruptedException");
    }
    return "email";
  }
}
