package com.zjl.community.cache;

import com.zjl.community.dto.TagDTO;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java","c","c++","php","perl","python","javascript","c#","ruby","go","lua","node.js","erlang","scala","bash","actionscript","golang","typescript","flutter"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("nux", "unix", "ubuntu", "windows-server", "centos", "负载均衡", "缓存", "apache", "nginx"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "nsqlite", "noracle", "sql", "nosql", "redis", "mongodb", "memcached", "postgresql"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("vim", "emacs", "ide", "eclipse", "xcode", "intellij-idea", "textmate", "sublime-text", "visual-studio", "git", "github", "svn", "hg", "docker", "ci"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}
