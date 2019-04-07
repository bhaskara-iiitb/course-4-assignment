package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String saveComment(@PathVariable("imageId") Integer imageId,
                              @RequestParam(value = "comment", required = false) String commentText,
                              HttpSession session) {

        Image image = imageService.getImage(imageId);
        //model.addAttribute("image", image);
        //model.addAttribute("tags", image.getTags());
        //model.addAttribute("comments", image.getComments());
        //return "images/image";

        User user = (User) session.getAttribute("loggeduser");
        Comment newComment = new Comment();
        newComment.setText(commentText);
        newComment.setUser(user);
        newComment.setImage(image);
        newComment.setDate(new Date());

        commentService.saveComment(newComment);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();

    }
}
