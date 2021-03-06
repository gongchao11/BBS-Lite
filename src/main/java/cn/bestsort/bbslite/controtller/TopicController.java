package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.pojo.model.Topic;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.TopicService;
import cn.bestsort.bbslite.vo.PublishVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopicController {
    private final TopicService topicService;
    private final ArticleService articleService;

    @GetMapping("/topic")
    public String topic(Model model){
        List<Topic> topics = topicService.getAllWithoutDefault();
        model.addAttribute("topics",topics);
        return "topic";
    }


    @GetMapping("/topic/{id}")
    public String comments(@PathVariable(name="id") Byte id,
                           @RequestParam(name="page",defaultValue = "1") Integer page,
                           @RequestParam(name="size",defaultValue = "10") Integer size,
                           Model model){
        Topic topic = topicService.getById(id);
        return "topic";
    }

}
