package com.example.tutorsFinderSystem.mapper;

import com.example.tutorsFinderSystem.dto.response.FeaturedTutorResponse;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FeaturedTutorMapper {

    default FeaturedTutorResponse toResponse(Object[] row) {
        FeaturedTutorResponse res = new FeaturedTutorResponse();
        res.setTutorId(toLong(row[0]));
        res.setFullName(toStr(row[1]));
        res.setAvatarUrl(buildAvatarUrl(toStr(row[2])));
        res.setSubject(toStr(row[3]));
        res.setAddress(toStr(row[4]));
        res.setPricePerHour(toInt(row[5]));
        res.setAverageRating(toDouble(row[6]));
        res.setTotalRatings(toLong(row[7]));
        return res;
    }

    // ===== helpers cast an toàn =====
    default Long toLong(Object v) {
        if (v == null)
            return null;
        if (v instanceof Number n)
            return n.longValue();
        return Long.valueOf(v.toString());
    }

    default Integer toInt(Object v) {
        if (v == null)
            return null;
        if (v instanceof Number n)
            return n.intValue();
        return Integer.valueOf(v.toString());
    }

    default Double toDouble(Object v) {
        if (v == null)
            return 0.0;
        if (v instanceof Number n)
            return n.doubleValue();
        return Double.valueOf(v.toString());
    }

    default String toStr(Object v) {
        return v == null ? null : v.toString();
    }

    // Avatar giống style bạn đang dùng
    default String buildAvatarUrl(String avatar) {
        if (avatar == null)
            return null;

        if (!avatar.contains("http")) {
            return "http://localhost:8080/tutorsFinder/drive/view/" + avatar;
        }

        if (avatar.contains("id=")) {
            String id = avatar.substring(avatar.indexOf("id=") + 3);
            int idx = id.indexOf("&");
            if (idx != -1)
                id = id.substring(0, idx);
            return "http://localhost:8080/tutorsFinder/drive/view/" + id;
        }

        return avatar;
    }
}
