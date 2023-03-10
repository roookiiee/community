function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    $.ajax({
       type:"POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response){
           if (response.code == 200){
               $("#comment-section").hide();
           }else {
               if (response.code == 2003){
                   var isAccepted = confirm(response.message);
                   if (isAccepted){
                       window.open("https://github.com/login/oauth/authorize?client_id=ab529c4bcbaa97f92b11&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                       window.localStorage.setItem("closable",true);
                   }
               }else {
                   alert(response.message);
               }
           }
            console.log(response);
        },
        dataType:"json",
        contentType:'application/json'
    });
    console.log(questionId);
    console.log(content);
}