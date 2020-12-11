# ProgressStepLine

ProgressStepLine shows animated progress indicator step by step

____

## Demo

![Alt-текст](https://user-images.githubusercontent.com/35424407/101927649-c72b8600-3be5-11eb-8f29-784c8865f0c0.gif "Img")

## Usage
____

### Gradle

```
dependencies {
    implementation 'com.github.artem-nr:progressstepline:1.1'
}
```

Use it in your own code:

```
<com.artemnr.progressstepline.ProgressStepLine
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

The Customized properties are in the follow table

| Property | Format | Default |
|----------------|:---------:|----------------:|
| maxProgress | Int | 5 |
| currentProgress | Int | 0 |
