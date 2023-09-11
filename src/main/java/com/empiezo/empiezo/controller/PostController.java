package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.config.UserPrincipal;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.dto.CommentDto;
import com.empiezo.empiezo.dto.PostDto;
import com.empiezo.empiezo.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class  PostController {
    private final PostService postService;

    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<PostDto.PostWriteDto> post(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid PostDto.PostWriteDto request) {

        Long currentLoginUserId = userPrincipal.getUserId();
        postService.write(request, currentLoginUserId);

        return ResponseEntity.ok(request);
    }

    @PatchMapping("/posts/{postId}")
    @ResponseBody
    public ResponseEntity<PostDto.PostModify> modify(@PathVariable Long postId, @RequestBody @Valid PostDto.PostModify request) {
        postService.modify(postId, request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/posts")
    public String postList(
            Model model,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> postList = postService.get(pageable);

        addAttributeWithModelForPagination(model, pageable, postList);

        return "board/boardlist";
    }

    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        PostDto.Response res = postService.get(postId);
        List<CommentDto.Response> comments = res.getComments();

        model.addAttribute("post", res);

        if(comments != null && comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        return "board/postdetails";
    }


    @GetMapping("/posts/search")
    public String postListGetBySearch(Model model,
                                     @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                     @RequestParam("select-search-type") String param,
                                      @RequestParam("context") String text) {
        Page<Post> postList = null;
        if("title".equals(param)) {
            postList = postService.searchByTitle(text, pageable);
        } else if("writer".equals(param)) {
            postList = postService.searchByWriter(text, pageable);
        }

        addAttributeWithModelForPagination(model, pageable, postList);

        return "board/boardlist";
    }

    @GetMapping("/posts/modify/{postId}")
    public String modifyPost(@PathVariable Long postId, Model model) {
        PostDto.Response res = postService.get(postId);

        model.addAttribute("post", res);
        return "board/boardpatch";
    }

    @GetMapping("/create-post")
    public String post() {
        return "board/boardcreate";
    }

    private void addAttributeWithModelForPagination(Model model, Pageable pageable, Page<Post> postList) {
        model.addAttribute("posts", postList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", postList.hasNext());
        model.addAttribute("hasPrev", postList.hasPrevious());
    }
}
