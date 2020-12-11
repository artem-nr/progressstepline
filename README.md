# ProgressStepLine

ProgressStepLine shows animated progress indicator step by step

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
|:----------------:|:---------:|:----------------:|
| maxProgress | integer | 5 |
| currentProgress | integer | 0 |
| previousProgress | Float | 0F |
| progressDuration | Long | 600L |
| enableProgressColor | color from resourses (Int) | #0099DA |
| disableProgressColor | color from resourses (Int) | #DFE4E8 |
| enableTextColor | color from resourses (Int) | #0099DA |
| disableTextColor |color from resourses (Int) | #C2CACF |
| progressTextSize | dimension | 14sp |
| progressMargin | dimension | 30dp |
| textMargin | dimension | 20dp |
| progressHeight | dimension | 2dp |
