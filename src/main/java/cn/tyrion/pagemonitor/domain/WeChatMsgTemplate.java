package cn.tyrion.pagemonitor.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WeChatMsgTemplate {

    private String toUser;

    private String templateId;

    // 模板消息详情链接
    private String url;

    // 消息顶部的颜色
    private String topColor;

    private List<ParamTemplate> paramTemplateList;

    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.templateId)).append(",");
        buffer.append(String.format("\"url\":\"%s\"", this.url)).append(",");
        buffer.append(String.format("\"topcolor\":\"%s\"", this.topColor)).append(",");
        buffer.append("\"data\":{");
        ParamTemplate param = null;
        for (int i = 0; i < this.paramTemplateList.size(); i++) {
            param = paramTemplateList.get(i);
            // 判断是否追加逗号
            if (i < this.paramTemplateList.size() - 1){

                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"},", param.getName(), param.getValue(), param.getColor()));
            }else{
                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));
            }

        }
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }
}
