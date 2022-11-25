package org.sjhstudio.lostark.util

fun getJewlImageUrl(jewlName: String?): String? {
    if (!jewlName.isNullOrEmpty()) {
        when {
            jewlName.contains("1레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_46.png"
            }
            jewlName.contains("1레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_56.png"
            }
            jewlName.contains("2레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_47.png"
            }
            jewlName.contains("2레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_57.png"
            }
            jewlName.contains("3레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_48.png"
            }
            jewlName.contains("3레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_58.png"
            }
            jewlName.contains("4레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_49.png"
            }
            jewlName.contains("4레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_59.png"
            }
            jewlName.contains("5레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_50.png"
            }
            jewlName.contains("5레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_60.png"
            }
            jewlName.contains("6레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_51.png"
            }
            jewlName.contains("6레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_61.png"
            }
            jewlName.contains("7레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_52.png"
            }
            jewlName.contains("7레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_62.png"
            }
            jewlName.contains("8레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_53.png"
            }
            jewlName.contains("8레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_63.png"
            }
            jewlName.contains("9레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_54.png"
            }
            jewlName.contains("9레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_64.png"
            }
            jewlName.contains("10레벨 멸화") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_55.png"
            }
            jewlName.contains("10레벨 홍염") -> {
                return "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Use/Use_9_65.png"
            }
            else -> {
                return null
            }
        }
    } else {
        return null
    }
}