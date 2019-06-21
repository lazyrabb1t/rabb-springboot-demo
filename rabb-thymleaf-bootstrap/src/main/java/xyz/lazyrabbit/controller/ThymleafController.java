package xyz.lazyrabbit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.lazyrabbit.model.TableResult;
import xyz.lazyrabbit.model.TreeNode;
import xyz.lazyrabbit.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ThymleafController {
    @GetMapping("/treeview")
    public String treeview() {
        return "treeview/index";
    }

    @GetMapping("/validator")
    public String validator() {
        return "validator/index";
    }

    @GetMapping("/table")
    public String table() {
        return "table/index";
    }

    @GetMapping("/getNodes")
    @ResponseBody
    public List<TreeNode> getNodes() {
        List<TreeNode> list = new ArrayList<TreeNode>();
        list.add(new TreeNode(999, "无限月读"));
        return list;
    }

    @GetMapping("/getUsers")
    @ResponseBody
    public TableResult<User> getUsers(@RequestParam(required = false, value = "offset", defaultValue = "0") Integer offset,
                                      @RequestParam(required = false, value = "limit", defaultValue = "5") Integer limit,
                                      @RequestParam(required = false, value = "order", defaultValue = "asc") String order,
                                      @RequestParam(required = false, value = "sort", defaultValue = "id") String sort,
                                      @RequestParam(required = false, value = "search", defaultValue = "") String serach) {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < limit; i++) {
            list.add(new User(offset + i + 1, "Benzema" + serach, serach + "@gmail.com", UUID.randomUUID().toString().replaceAll("-", "")));
        }
        return new TableResult<>(list.size() * 10 + 1, list);
    }
}
