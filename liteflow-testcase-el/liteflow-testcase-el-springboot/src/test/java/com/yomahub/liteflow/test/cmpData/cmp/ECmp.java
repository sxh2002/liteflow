package com.yomahub.liteflow.test.cmpData.cmp;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.test.cmpData.vo.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("e")
public class ECmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        List<User> empty = getCmpDataList(User.class);
        DefaultContext context = getFirstContextBean();
        context.setData("empty", empty);
        System.out.println("ECmp executed!");
    }
}
