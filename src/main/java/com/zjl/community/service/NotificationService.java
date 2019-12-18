package com.zjl.community.service;

import com.zjl.community.dto.NotificationDTO;
import com.zjl.community.dto.PaginationDTO;
import com.zjl.community.dto.QuestionDTO;
import com.zjl.community.enums.NotificationStatusEnum;
import com.zjl.community.enums.NotificationTypeEnum;
import com.zjl.community.exception.CustomizeErrorCode;
import com.zjl.community.exception.CustomizeException;
import com.zjl.community.mapper.NotificationMapper;
import com.zjl.community.mapper.UserMapper;
import com.zjl.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        Integer offset = size * (page - 1);

        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();

        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }


        paginationDTO.setData(notificationDTOS);
        NotificationExample example1 = new NotificationExample();
        example1.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(example1);

        paginationDTO.setPagination(totalCount, page, size);

        return paginationDTO;

    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {

        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
