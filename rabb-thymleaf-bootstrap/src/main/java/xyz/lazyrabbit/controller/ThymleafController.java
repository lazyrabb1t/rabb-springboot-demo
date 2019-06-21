package xyz.lazyrabbit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.lazyrabbit.model.TableResult;
import xyz.lazyrabbit.model.TreeNode;
import xyz.lazyrabbit.model.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymleafController {
    @GetMapping("/treeview")
    public String treeview(){
        return "treeview/index";
    }
    @GetMapping("/validator")
    public String validator(){
        return "validator/index";
    }
    @GetMapping("/table")
    public String table(){
        return "table/index";
    }
    @GetMapping("/getNodes")
    @ResponseBody
    public List<TreeNode> getNodes(){
        List<TreeNode> list = new ArrayList<TreeNode>();
        list.add(new TreeNode(999, "无限月读"));
        return list;
    }
    @GetMapping("/getUsers")
    @ResponseBody
    public TableResult<User> getUsers(){
        List<User> list = new ArrayList<User>();
        list.add(new User(1,"Messi","a","1111"));
        list.add(new User(2,"Lucas","a","22222"));
        list.add(new User(3,"Neymar","a","333333"));
        list.add(new User(4,"CR7","a","44444"));
        list.add(new User(5,"Bale","a","55555"));
        list.add(new User(6,"Benzema","a","66666"));
        list.add(new User(6,"Benzema","a","66666"));
        return new TableResult<>(list.size(),list);
    }
}
