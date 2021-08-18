package com.mqz.elk.common.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：2021/5/25 11:34 上午
 * @Description
 * @About： https://github.com/DemoMeng
 */
@Data
@Accessors(chain = true)
public class OffHireDetailVO implements Serializable {
    private static final long serialVersionUID = -2606060642174512575L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "原缴纳的房屋押金")
    private BigDecimal houseDeposit;
    private String returnDate;
    @ApiModelProperty(value = "房屋视频-集合，方便前端直接取")
    private String[] videos;
    @ApiModelProperty(value = "房间集合")
    private List<OffHireContractDetailVO> detailVOList;
    @ApiModelProperty(value = "账单集合")
    private List<OffHireBillVO> billVOList;
    @ApiModelProperty(value = "抄送人集合")
    private List<String> copyRecipients;
    @ApiModelProperty(value = "审批人信息")
    private List<ApprovalByInfo> reviewerIdList;

    @Data
    @Accessors(chain = true)
    public static class ApprovalByInfo implements Serializable{
        @ApiModelProperty(value = "审批人")
        private String userid;
        @ApiModelProperty(value = "姓名")
        private String name;
        @ApiModelProperty(value = "头像")
        private String avatar;
        @ApiModelProperty(value = "审批流程id")
        private Integer approvalProcessId;
        @ApiModelProperty(value = "审批明细id")
        private Integer approvalProcessInfoId;
        @ApiModelProperty(value = "审批人")
        private String approvedBy;
        @ApiModelProperty(value = "反馈意见")
        private String feedback;
        @ApiModelProperty(value = "审批结果")
        private String results;
        @ApiModelProperty(value = "时间")
        private Date createTime;
    }
}
