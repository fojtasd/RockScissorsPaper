import java.awt.*;

class MyCanvas extends Canvas {
    private int x, y; // Position of the ball
    private int dx, dy; // Velocity of the ball
    private Image buffer; // Double buffering image
    private final Color color;

    public MyCanvas() {
        this.x = 50;
        this.y = 50;
        this.dx = 2;
        this.dy = 2;
        this.color = Color.RED;

        // Start the animation
        startAnimation();
    }

    public MyCanvas(int x, int y, int dx, int dy, String color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        switch (color) {
            case "red" -> this.color = Color.RED;
            case "green" -> this.color = Color.GREEN;
            case "blue" -> this.color = Color.BLUE;
            default -> this.color = Color.MAGENTA;
        }

        // Start the animation
        startAnimation();
    }

    private void startAnimation() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                update(); // Update the position of the ball
                repaint(); // Repaint the canvas

                try {
                    Thread.sleep(10); // Delay between each frame
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        animationThread.start();
    }

    private void update() {
        // Update the position of the ball
        x += dx;
        y += dy;

        // Reverse the direction if the ball reaches the edge of the canvas
        if (x <= 0 || x >= getWidth() - 50) {
            dx *= -1;
        }
        if (y <= 0 || y >= getHeight() - 50) {
            dy *= -1;
        }
    }

    @Override
    public void update(Graphics g) {
        if (buffer == null || buffer.getWidth(null) != getWidth() || buffer.getHeight(null) != getHeight()) {
            // Create the double buffering image with appropriate dimensions
            buffer = createImage(getWidth(), getHeight());
        }

        // Double buffering: draw on the buffer
        Graphics bufferGraphics = buffer.getGraphics();
        paint(bufferGraphics);

        // Draw the buffer on the canvas
        g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        // Clear the canvas
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the ball

        g.setColor(this.color);
        g.fillOval(x, y, 50, 50);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
}
