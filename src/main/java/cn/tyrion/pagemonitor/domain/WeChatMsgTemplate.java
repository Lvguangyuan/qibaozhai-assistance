package cn.tyrion.pagemonitor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class WeChatMsgTemplate {

    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("template_id")
    private String templateId;

    // 模板消息详情链接
    private String url;

    // 消息顶部的颜色
    @JsonProperty("topcolor")
    private String topColor;

    private Map<String, ParamTemplate> data;

}
