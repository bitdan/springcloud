package com.example.Responsibility;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:53
 */
public class ChainPatternDemo {
    public static void main(String[] args) {
        Ahandler ahandler = new Ahandler();
        Bhandler bhandler = new Bhandler();
        ahandler.setHandler(bhandler);
        Post post = new Post();
        post.setContext("正常内容, 广告, 游戏推广");
        System.out.println("过滤前的内容:" + post.getContext());
        ahandler.handlerRequest(post);
        System.out.println("过滤后的内容:" + post.getContext());
    }
}
