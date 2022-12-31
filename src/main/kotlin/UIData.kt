import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import javax.swing.JFrame
import javax.swing.JPanel

class G : JPanel() {
    var coordinates = intArrayOf(100, 20)
    var mar = 50
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g1 = g as Graphics2D
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        val width = width
        val height = height
        g1.draw(Line2D.Double(mar.toDouble(), mar.toDouble(), mar.toDouble(), (height - mar).toDouble()))
        g1.draw(
            Line2D.Double(
                mar.toDouble(),
                (height - mar).toDouble(),
                (width - mar).toDouble(),
                (height - mar).toDouble()
            )
        )
        val x = (width - 2 * mar).toDouble() / (coordinates.size - 1)
        val scale = (height - 2 * mar).toDouble() / max
        g1.paint = Color.red
        for (i in coordinates.indices) {
            val x1 = mar + i * x
            val y1 = height - mar - scale * coordinates[i]
            g1.fill(Ellipse2D.Double(x1 - 2, y1 - 2, 4.0, 4.0))

        }
    }

    private val max: Int
        private get() {
            var max = -Int.MAX_VALUE
            for (i in coordinates.indices) {
                if (coordinates[i] > max) max = coordinates[i]
            }
            return max
        }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val frame = JFrame()
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.add(G())
            frame.setSize(400, 400)
            frame.setLocation(200, 200)
            frame.isVisible = true
        }
    }
}