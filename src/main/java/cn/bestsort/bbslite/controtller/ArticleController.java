package cn.bestsort.bbslite.controtller;

import cn.bestsort.bbslite.aop.annotation.Cache;
import cn.bestsort.bbslite.aop.annotation.NeedLogin;
import cn.bestsort.bbslite.dto.ArticleQueryDto;
import cn.bestsort.bbslite.dto.ResultDto;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.enums.PeopleCenterEnum;
import cn.bestsort.bbslite.pojo.model.Article;
import cn.bestsort.bbslite.pojo.model.User;
import cn.bestsort.bbslite.service.ArticleService;
import cn.bestsort.bbslite.service.FollowService;
import cn.bestsort.bbslite.service.ThumbUpService;
import cn.bestsort.bbslite.service.UserService;
import cn.bestsort.bbslite.vo.ArticleDetailOptionVo;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 文章控制器,用于查询文章列表
 * @author bestsort
 * @date 19-8-31 下午8:35
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ThumbUpService thumbUpService;
    private final FollowService followService;
    @GetMapping("/article/{id}")
    public String article(@PathVariable("id") Long id){
        return "article";
    }

    /**
     * 获取文章列表(可根据搜索内容/话题/分类筛选结果)
     * @param queryDto 封装查询类
     * @return 根据条件筛选并进行分页后的文章列表
     */
    @Cache(min = 5,max = 10)
    @ResponseBody
    @GetMapping("/loadArticleList")
    public Object getArticleList(ArticleQueryDto queryDto){
        PageInfo<Article> pageInfo = articleService.getPageBySearch(queryDto);
        return new ResultDto().okOf()
                .addMsg("page",pageInfo)
                .addMsg("func", PeopleCenterEnum.ARTICLE);
    }

    /**
     * 加载文章详情
     * @param id 文章id
     * @return 文章详情
     */
    @Cache(max = 20,min = 10)
    @ResponseBody
    @GetMapping("/loadArticleDetail")
    public Object getArticleDetail(@RequestParam(name = "id") Long id){
        Article article = articleService.getArticleDetail(id);
        articleService.incArticleView(id,1L);
        User user = userService.getSimpleInfoById(article.getCreator());
        return new ResultDto().okOf()
                .addMsg("article",article)
                .addMsg("user",user);
    }

    @ResponseBody
    @GetMapping("/loadArticleOption")
    public Object getArticleOption(@RequestParam(name = "articleId") Long articleId,
                                       @RequestParam(name = "userId") Long userId,
                                       HttpSession session){
        ArticleDetailOptionVo articleDetailOptionVo = new ArticleDetailOptionVo();
        User user = (User)session.getAttribute("user");
        if(user != null && user.getId().equals(userId)){
            articleDetailOptionVo.setIsCreator(false);
        }

        if(user != null) {
            articleDetailOptionVo.setIsThumbUpArticle(
                    thumbUpService.getStatusByUser(articleId, user.getId())
            );
            articleDetailOptionVo.setIsFollowArticle(
                    followService.getStatusByUser(articleId,user.getId(), FunctionItem.ARTICLE)
            );
        }else {
            articleDetailOptionVo.setIsThumbUpArticle(false);
            articleDetailOptionVo.setIsFollowArticle(false);
        }
        return new ResultDto().okOf().addMsg("options",articleDetailOptionVo);
    }
}
