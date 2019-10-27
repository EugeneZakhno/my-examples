package io.github.eugenezakhno;

import io.github.eugenezakhno.domain.Message;
import io.github.eugenezakhno.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/main")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main (Map <String, Object> model){
     Iterable<Message> messages =   messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model){
       Message message = new Message(text, tag);
       messageRepo.save(message);
        Iterable<Message> messages =   messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable <Message> messages;
        if (filter != null && !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        return "main";
    }
}

