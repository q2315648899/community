package life.majiang.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    // 对象属性命名是啥，转化成的json字符串参数命名就是啥
    // 这些都是post请求体中带的json参数固定的命名，若不使用这个命名使用驼峰命名clientId，
    // 构造成json字符串时也会使用clientId参数名称和client_id不一致导致报错。
    // 若想使用驼峰命名，在指定属性上添加@JSONField(name = "XXX")注解，XXX是json中参数命名，下面的属性就可以用clientId驼峰命名了
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
