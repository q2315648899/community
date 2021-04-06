package life.majiang.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;// json字符串中命名为avatar_url，此处属性也可写成avatarUrl驼峰命名，fastjson把json字符串转换成对象时会自动识别对象中的驼峰命名属性
}
