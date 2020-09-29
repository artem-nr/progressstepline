package com.artemnr.progressstepline

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ProgressStepLine @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs)  {

    private var typedArray: TypedArray? = null
    private var paintEnableProgress: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintDisableProgress: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var paintText: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val defaultViewWidth = resources.getDimension(R.dimen.default_view_width).toInt()
    private val defaultViewHeight = resources.getDimension(R.dimen.default_view_height).toInt()
    private var progressHeight = resources.getDimension(R.dimen.default_progress_height)
    private var progressTextSize = resources.getDimension(R.dimen.text_size)
    private var defaultTextMargin = resources.getDimension(R.dimen.default_text_margin)
    private var defaultProgressMargin = resources.getDimension(R.dimen.default_progress_margin)

    private var maxProgress: Int = DEFAULT_PROGRESS_SIZE
    private var currentProgress: Int = DEFAULT_CURRENT_STEP
    private var previousProgress: Float = DEFAULT_NUMBER_ZERO_FLOAT
    private var progressDuration: Long = DEFAULT_DURATION
    private var enableProgressColor: Int = ContextCompat.getColor(context, R.color.c_0099DA)
    private var disableProgressColor: Int = ContextCompat.getColor(context, R.color.c_DFE4E8)
    private var enableTextColor = ContextCompat.getColor(context, R.color.c_0099DA)
    private var disableTextColor = ContextCompat.getColor(context, R.color.c_C2CACF)

    private var progressTextFont = ResourcesCompat.getFont(context, R.font.regular)

    private var animator: ValueAnimator? = null
    private var currentProgressAnimate = DEFAULT_NUMBER_NEGATIVE

    companion object {
        private const val DEFAULT_PROGRESS_SIZE = 5
        private const val DEFAULT_CURRENT_STEP = 0
        private const val DEFAULT_DURATION = 600L
        private const val DEFAULT_NUMBER_NEGATIVE = -1F
        private const val DEFAULT_NUMBER_ZERO_FLOAT = 0F
        private const val DEFAULT_NUMBER_ZERO_LONG = 0L
        private const val DEFAULT_NUMBER_ZERO_INT = 0
        private const val DEFAULT_TEXT_LENGTH = 2
        private const val DEFAULT_PROGRESS_BEGIN = 1
    }

    init {
        attrs?.let {
            initAttributes(context, attrs)
        }
        initProgress()
        initTextPaint()
    }

    override fun onDraw(canvas: Canvas?) {
        initStartProgress()
        drawProgressLineEnable(canvas = canvas)
        drawProgressLineDisable(canvas = canvas)
        drawText(canvas = canvas)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when(widthMode) {
            MeasureSpec.AT_MOST -> defaultViewWidth
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.UNSPECIFIED -> defaultViewWidth
            else -> defaultViewWidth
        }
        val height = when(heightMode) {
            MeasureSpec.AT_MOST -> defaultViewHeight
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.UNSPECIFIED -> defaultViewHeight
            else -> defaultViewHeight
        }
        setMeasuredDimension(width, height)
    }


    fun setMaxProgress(progress: Int) {
        maxProgress = progress
        invalidate()
    }

    fun setCurrentProgress(progress: Int) {
        currentProgress = progress
        invalidate()
        startAnimation()
    }

    fun setDuration(duration: Long) {
        progressDuration =
            if (duration < DEFAULT_NUMBER_ZERO_LONG)
                DEFAULT_DURATION
            else
                duration
    }

    fun setEnableProgressColor(color: Int) {
        enableProgressColor = color
        paintEnableProgress.color = enableProgressColor
        invalidate()
    }

    fun setDisableProgressColor(color: Int) {
        disableProgressColor = color
        paintDisableProgress.color = disableProgressColor
        invalidate()
    }

    fun setEnableTextColor(color: Int) {
        enableTextColor = color
        invalidate()
    }

    fun setDisableTextColor(color: Int) {
        disableTextColor = color
        invalidate()
    }

    fun setTextSize(textSize: Float) {
        progressTextSize = textSize
        paintText.textSize = progressTextSize
        invalidate()
    }

    fun setProgressMargin(margin: Float) {
        defaultProgressMargin = margin
        invalidate()
    }

    fun setTextMargin(margin: Float) {
        defaultTextMargin = margin
        invalidate()
    }

    fun setProgressHeight(height: Float) {
        progressHeight = height
        invalidate()
    }

    private fun initProgress() {
        paintEnableProgress.color = enableProgressColor
        paintDisableProgress.color = disableProgressColor
    }

    private fun initTextPaint() {
        paintText.textSize = progressTextSize
        paintText.color = disableTextColor
        paintText.textAlign = Paint.Align.LEFT
        paintText.typeface = progressTextFont
    }

    private fun initAttributes(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressStepLine)
        this@ProgressStepLine.typedArray = typedArray
        val mProgress = typedArray.getInt(R.styleable.ProgressStepLine_maxProgress, DEFAULT_PROGRESS_SIZE)
        maxProgress =
            if (mProgress < DEFAULT_NUMBER_ZERO_INT)
                DEFAULT_PROGRESS_SIZE
            else
                mProgress
        currentProgress = typedArray.getInt(R.styleable.ProgressStepLine_currentProgress, DEFAULT_CURRENT_STEP)
        val pDuration = typedArray.getInt(R.styleable.ProgressStepLine_progressDuration, DEFAULT_DURATION.toInt())
        progressDuration =
            if (pDuration < DEFAULT_NUMBER_ZERO_INT)
                DEFAULT_DURATION
            else
                pDuration.toLong()
        enableProgressColor = typedArray.getColor(R.styleable.ProgressStepLine_enableProgressColor, ContextCompat.getColor(context, R.color.c_0099DA))
        disableProgressColor = typedArray.getColor(R.styleable.ProgressStepLine_disableProgressColor, ContextCompat.getColor(context, R.color.c_DFE4E8))
        enableTextColor = typedArray.getColor(R.styleable.ProgressStepLine_enableProgressColor, ContextCompat.getColor(context, R.color.c_0099DA))
        disableTextColor = typedArray.getColor(R.styleable.ProgressStepLine_disableProgressColor, ContextCompat.getColor(context, R.color.c_C2CACF))
        progressTextSize = typedArray.getDimension(R.styleable.ProgressStepLine_progressTextSize, resources.getDimension(R.dimen.text_size))
        defaultProgressMargin = typedArray.getDimension(R.styleable.ProgressStepLine_progressMargin, resources.getDimension(R.dimen.default_progress_margin))
        defaultTextMargin = typedArray.getDimension(R.styleable.ProgressStepLine_textMargin, resources.getDimension(R.dimen.default_text_margin))
        progressHeight = typedArray.getDimension(R.styleable.ProgressStepLine_progressHeight, resources.getDimension(R.dimen.default_progress_height))
        typedArray.recycle()
    }

    private fun drawProgressLineEnable(canvas: Canvas?) {
        canvas?.drawRect(
            DEFAULT_NUMBER_ZERO_FLOAT,
            DEFAULT_NUMBER_ZERO_FLOAT + defaultProgressMargin,
            currentProgressAnimate,
            progressHeight + defaultProgressMargin,
            paintEnableProgress)
    }

    private fun drawProgressLineDisable(canvas: Canvas?) {
        canvas?.drawRect(
            currentProgressAnimate,
            DEFAULT_NUMBER_ZERO_FLOAT + defaultProgressMargin,
            width.toFloat() + defaultProgressMargin,
            progressHeight + defaultProgressMargin,
            paintDisableProgress)
    }

    private fun drawText(canvas: Canvas?) {
        var textPositionX = DEFAULT_NUMBER_ZERO_FLOAT
        for (textCount in DEFAULT_PROGRESS_BEGIN..maxProgress) {
            val number =
                if (textCount.toString().length < DEFAULT_TEXT_LENGTH )
                    "$DEFAULT_NUMBER_ZERO_INT$textCount"
                else "$textCount"
            paintText.color =
                if (textCount <= currentProgress)
                    enableTextColor
                else
                    disableTextColor
            canvas?.drawText(number, textPositionX, defaultTextMargin, paintText)
            textPositionX += (width / maxProgress.toFloat())
        }
    }

    private fun initStartProgress() {
        if (currentProgressAnimate < DEFAULT_NUMBER_ZERO_INT ) {
            currentProgressAnimate = calculateProgress()
            previousProgress = calculateProgress()
        }
    }

    private fun startAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(previousProgress, calculateProgress()).apply {
            duration = progressDuration
            interpolator = LinearInterpolator()
            addUpdateListener { anim ->
                currentProgressAnimate = anim.animatedValue as Float
                invalidate()
            }
        }
        animator?.start()
        previousProgress = calculateProgress()
    }

    private fun validateValue(): Boolean {
        return maxProgress < currentProgress ||
                maxProgress < DEFAULT_NUMBER_ZERO_INT ||
                currentProgress < DEFAULT_NUMBER_ZERO_INT
    }

    private fun calculateProgress(): Float {
        return if (validateValue())
            width.toFloat()
        else
            (width.toFloat() / maxProgress.toFloat()) * currentProgress
    }
}