package com.jonasestevam.streaming.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.jonasestevam.streaming.dto.VideoDTO;
import com.jonasestevam.streaming.model.Video;

@Mapper(componentModel = "spring")
public interface VideoMapper extends BaseMapper<VideoDTO, Video> {
    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);


    @AfterMapping
    default void setCategoryName(@MappingTarget VideoDTO videoDTO, Video video) {
        if (video.getCategory() != null) {
            videoDTO.setCategoryName(video.getCategory().getName());
        }
    }

    @AfterMapping
    default void setCategoryDescription(@MappingTarget VideoDTO videoDTO, Video video) {
        if (video.getCategory() != null) {
            videoDTO.setCategoryDescription(video.getCategory().getDescription());
        }
    }
}