# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

**项目名称**: android_tools
**应用包名**: com.example.myapplicationtest
**性质**: 多模块 Android 应用，包含主应用模块 `app` 和网络库模块 `network`

## 构建命令

```bash
# Debug 构建
./gradlew assembleDebug

# Release 构建
./gradlew assembleRelease

# 清理并构建
./gradlew clean assembleDebug

# 构建并安装到设备
./gradlew installDebug

# 查看所有可用任务
./gradlew tasks
```

## 项目架构

### 模块结构

```
app/                  # 主应用模块
├── activitys/        # 18个Activity
├── base/             # 基类 (MyApplication, BaseActivity, BaseViewModel)
├── bean/             # 数据模型
├── db/               # Room数据库
├── dialog/           # 对话框组件
├── extensions/       # Kotlin扩展函数库
├── ktx/              # Kotlin扩展
├── loading/          # 加载状态View
├── net/              # 网络相关 (ApiService, RetrofitClient)
├── service/          # 后台服务 (MusicService)
├── utils/            # 工具类
├── view/             # 自定义View (FlowLayout, DraggableCircleView)
└── vm/               # ViewModel

network/              # 网络库模块
├── base/             # 网络基础类 (BaseRetrofitClient, NetworkRequest)
└── entity/           # 网络实体
```

### 技术栈

| 类别 | 技术 |
|------|------|
| DI | Hilt 2.44 |
| 网络 | Retrofit 2.9.0 + OkHttp 4.9.0 |
| 数据库 | Room 2.5.0 |
| 视频 | DKPlayer 3.3.7 |
| 图片 | Glide 4.16.0 |
| 权限 | PermissionX 1.7.1 |
| 相机 | CameraX 1.3.0 |
| 分页 | Paging3 3.1.1 |

### Gradle 配置

- **Gradle**: 6.8.3
- **AGP**: 4.2.2
- **Kotlin**: 1.8.0
- **App compileSdk**: 34
- **Network compileSdk**: 32

## 重要配置

- **ViewBinding**: 已启用
- **签名文件**: `test.jks` (调试用)
- **本地SDK路径**: `C:\Users\cairong\AppData\Local\Android\Sdk`

## 代码规范

- Kotlin扩展函数库文档: `app/src/main/java/com/example/myapplicationtest/extensions/README.md`
- 代码风格: 在 `gradle.properties` 中定义 `kotlin.code.style=official`

## 注意事项

1. **测试目录为空** - `app/src/test/` 和 `app/src/androidTest/` 目前没有实际测试代码
2. **SDK版本不一致** - app模块(34)和network模块(32)的compileSdk不同，修改时需注意
3. **ProGuard未启用** - `minifyEnabled false`，发布版本未启用代码混淆
