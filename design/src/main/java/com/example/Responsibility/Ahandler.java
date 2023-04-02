package com.example.Responsibility;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:47
 */
public class Ahandler extends  PostHandler{
    @Override
    public void handlerRequest(Post post) {
        String context = post.getContext();
        context = context.replaceAll("广告", "***");
        post.setContext(context);
        System.out.println("过滤广告");
        next(post);
    }
}
