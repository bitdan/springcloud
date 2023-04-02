package com.example.Responsibility;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:49
 */
public class Bhandler extends  PostHandler{
    @Override
    public void handlerRequest(Post post) {
        String context = post.getContext();
        context=context.replaceAll("游戏推广","---");
        post.setContext(context);
        System.out.println("过滤游戏推广");
        next(post);
    }
}
