package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.model.Rooms;
import com.example.hotelmanagementsystem.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoomController {

  private final RoomService roomService;

  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }

  @GetMapping("/room")
  public String createRooms(Model model){
    model.addAttribute("rooms",new Rooms());
    return "admin/roomForm";
  }

  @PostMapping("/room")
  public String processRooms(Rooms rooms, BindingResult result,Model model){
    if(result.hasErrors()){
      model.addAttribute("rooms",rooms);
      return "admin/roomForm";
    }
    roomService.create(rooms);
    return "redirect:/rooms";
  }

  @GetMapping("/rooms")
  public String showRooms(Model model){
    model.addAttribute("rooms",roomService.findAll());
    model.addAttribute("deletesuccess",model.containsAttribute("delete"));
    return "admin/rooms";
  }

  @GetMapping("/room/{id}")
  public String showRoomDetails(Model model,@PathVariable("id") long id){
    model.addAttribute("room",roomService.findById(id));
    return "admin/roomdetail";
  }
  @GetMapping("/room/delete/{id}")
  public String deleteRoom(@PathVariable("id") long id, RedirectAttributes redirectAttributes){
      roomService.delete(id);
      redirectAttributes.addFlashAttribute("delete",true);
      return "redirect:/rooms";
  }
}
