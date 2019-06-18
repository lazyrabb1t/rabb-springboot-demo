package xyz.lazyrabbit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.lazyrabbit.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymleafController {
    @GetMapping("/")
    public String index(){

        return "treeview/index";
    }
    @GetMapping("/getNodes")
    @ResponseBody
    public List<TreeNode> getNodes(){
        List<TreeNode> list = new ArrayList<TreeNode>();
        list.add(new TreeNode(999, "无限月读"));
        return list;
    }
}
