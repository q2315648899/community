/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=9bc3a57af8f4b89a9219&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}


function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    // .collapse hides content
    // .collapse.in hides content
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    comments.toggleClass("in");
    if (comments.hasClass("in")) {
        // 展开二级评论
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {

            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                // .reverse()调换数组顺序
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });

                e.classList.add("active");

            });
        }

    } else {
        // 折叠二级评论
        e.classList.remove("active");
    }

    // jQuery addClass()，removeClass()  JQuery获取并设置CSS类的方法
    // e.classList.add（） e.classList.remove（）原生JavaScript获取并设置CSS类的方法
    // var collapse = e.getAttribute("data-collapse");
    // if (collapse) {
    //     comments.removeClass("in");
    //     e.removeAttribute("data-collapse");
    //     e.classList.remove("active");
    // } else {
    //     comments.addClass("in");
    //     e.setAttribute("data-collapse", "in");
    //     e.classList.add("active");
    // }
}

function selectTag (e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

function showSelectTag () {
    $("#select-tag").show();
}