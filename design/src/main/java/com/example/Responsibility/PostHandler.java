package com.example.Responsibility;

/**
 * @author duran
 * @description TODO
 * @date 2022-12-27 0:43
 */
public abstract class PostHandler {
    private PostHandler handler;

    public void setHandler(PostHandler handler) {
        this.handler = handler;
    }

    public abstract void handlerRequest(Post post);

    protected final void next(Post post) {
        if (this.handler != null) {
            this.handler.handlerRequest(post);
        }
    }
}
