package com.ruoyi.plough.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName soil_data
 */
@TableName(value ="soil_data")
@Data
public class SoilData implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer soilId;

    /**
     * 土种所在地 varchar(255)
     */
    private String soilLocation;

    /**
     * 典型剖面采集地点varchar(255)
     */
    private String collectionSite;

    /**
     * 发生层次及理化性质varchar(255)
     */
    private String levelPhysical;

    /**
     * 土种编号int
     */
    private Integer soilTypeCode;

    /**
     * 土壤类型varchar(255)
     */
    private String soilType;

    /**
     * 土壤亚类varchar(255)
     */
    private String soilSubtype;

    /**
     * 土壤名称varchar(255)
     */
    private String soilName;

    /**
     * 描述text
     */
    private String description;

    /**
     * landform varchar(255)
     */
    private String landform;

    /**
     * 面积(公顷) int
     */
    private Integer areaHectares;

    /**
     * 面积(万亩) int
     */
    private Integer areaM;

    /**
     * 母质varchar(255)
     */
    private String parentMaterial;

    /**
     * 剖面构型varchar(255)
     */
    private String profileConfiguration;

    /**
     * 有效土体深度varchar(255)
     */
    private String effectiveSoilDepth;

    /**
     * 主要性状varchar(255)
     */
    private String mainTraits;

    /**
     * 生产障碍因子varchar(255)
     */
    private String prodFactor;

    /**
     * 生产性能varchar(255)
     */
    private String prodPerformance;

    /**
     * 土壤用途varchar(255)
     */
    private String soilUsage;

    /**
     * 备注varchar(255)
     */
    private String notes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SoilData other = (SoilData) that;
        return (this.getSoilId() == null ? other.getSoilId() == null : this.getSoilId().equals(other.getSoilId()))
            && (this.getSoilLocation() == null ? other.getSoilLocation() == null : this.getSoilLocation().equals(other.getSoilLocation()))
            && (this.getCollectionSite() == null ? other.getCollectionSite() == null : this.getCollectionSite().equals(other.getCollectionSite()))
            && (this.getLevelPhysical() == null ? other.getLevelPhysical() == null : this.getLevelPhysical().equals(other.getLevelPhysical()))
            && (this.getSoilTypeCode() == null ? other.getSoilTypeCode() == null : this.getSoilTypeCode().equals(other.getSoilTypeCode()))
            && (this.getSoilType() == null ? other.getSoilType() == null : this.getSoilType().equals(other.getSoilType()))
            && (this.getSoilSubtype() == null ? other.getSoilSubtype() == null : this.getSoilSubtype().equals(other.getSoilSubtype()))
            && (this.getSoilName() == null ? other.getSoilName() == null : this.getSoilName().equals(other.getSoilName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getLandform() == null ? other.getLandform() == null : this.getLandform().equals(other.getLandform()))
            && (this.getAreaHectares() == null ? other.getAreaHectares() == null : this.getAreaHectares().equals(other.getAreaHectares()))
            && (this.getAreaM() == null ? other.getAreaM() == null : this.getAreaM().equals(other.getAreaM()))
            && (this.getParentMaterial() == null ? other.getParentMaterial() == null : this.getParentMaterial().equals(other.getParentMaterial()))
            && (this.getProfileConfiguration() == null ? other.getProfileConfiguration() == null : this.getProfileConfiguration().equals(other.getProfileConfiguration()))
            && (this.getEffectiveSoilDepth() == null ? other.getEffectiveSoilDepth() == null : this.getEffectiveSoilDepth().equals(other.getEffectiveSoilDepth()))
            && (this.getMainTraits() == null ? other.getMainTraits() == null : this.getMainTraits().equals(other.getMainTraits()))
            && (this.getProdFactor() == null ? other.getProdFactor() == null : this.getProdFactor().equals(other.getProdFactor()))
            && (this.getProdPerformance() == null ? other.getProdPerformance() == null : this.getProdPerformance().equals(other.getProdPerformance()))
            && (this.getSoilUsage() == null ? other.getSoilUsage() == null : this.getSoilUsage().equals(other.getSoilUsage()))
            && (this.getNotes() == null ? other.getNotes() == null : this.getNotes().equals(other.getNotes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSoilId() == null) ? 0 : getSoilId().hashCode());
        result = prime * result + ((getSoilLocation() == null) ? 0 : getSoilLocation().hashCode());
        result = prime * result + ((getCollectionSite() == null) ? 0 : getCollectionSite().hashCode());
        result = prime * result + ((getLevelPhysical() == null) ? 0 : getLevelPhysical().hashCode());
        result = prime * result + ((getSoilTypeCode() == null) ? 0 : getSoilTypeCode().hashCode());
        result = prime * result + ((getSoilType() == null) ? 0 : getSoilType().hashCode());
        result = prime * result + ((getSoilSubtype() == null) ? 0 : getSoilSubtype().hashCode());
        result = prime * result + ((getSoilName() == null) ? 0 : getSoilName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getLandform() == null) ? 0 : getLandform().hashCode());
        result = prime * result + ((getAreaHectares() == null) ? 0 : getAreaHectares().hashCode());
        result = prime * result + ((getAreaM() == null) ? 0 : getAreaM().hashCode());
        result = prime * result + ((getParentMaterial() == null) ? 0 : getParentMaterial().hashCode());
        result = prime * result + ((getProfileConfiguration() == null) ? 0 : getProfileConfiguration().hashCode());
        result = prime * result + ((getEffectiveSoilDepth() == null) ? 0 : getEffectiveSoilDepth().hashCode());
        result = prime * result + ((getMainTraits() == null) ? 0 : getMainTraits().hashCode());
        result = prime * result + ((getProdFactor() == null) ? 0 : getProdFactor().hashCode());
        result = prime * result + ((getProdPerformance() == null) ? 0 : getProdPerformance().hashCode());
        result = prime * result + ((getSoilUsage() == null) ? 0 : getSoilUsage().hashCode());
        result = prime * result + ((getNotes() == null) ? 0 : getNotes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", soilId=").append(soilId);
        sb.append(", soilLocation=").append(soilLocation);
        sb.append(", collectionSite=").append(collectionSite);
        sb.append(", levelPhysical=").append(levelPhysical);
        sb.append(", soilTypeCode=").append(soilTypeCode);
        sb.append(", soilType=").append(soilType);
        sb.append(", soilSubtype=").append(soilSubtype);
        sb.append(", soilName=").append(soilName);
        sb.append(", description=").append(description);
        sb.append(", landform=").append(landform);
        sb.append(", areaHectares=").append(areaHectares);
        sb.append(", areaM=").append(areaM);
        sb.append(", parentMaterial=").append(parentMaterial);
        sb.append(", profileConfiguration=").append(profileConfiguration);
        sb.append(", effectiveSoilDepth=").append(effectiveSoilDepth);
        sb.append(", mainTraits=").append(mainTraits);
        sb.append(", prodFactor=").append(prodFactor);
        sb.append(", prodPerformance=").append(prodPerformance);
        sb.append(", soilUsage=").append(soilUsage);
        sb.append(", notes=").append(notes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
