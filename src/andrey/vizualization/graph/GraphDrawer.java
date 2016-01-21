package andrey.vizualization.graph;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.Callbacks.*;


public class GraphDrawer {
    boolean fullscreen = false;
    boolean mouseDown = false;
    int triangleSize;
    private int mouseX, mouseY;
    private int mouseDX, mouseDY;
    private int xAngle = 0, yAngle = 0;
    GLFWErrorCallback errorCallback;
    GLFWKeyCallback   keyCallback;
    GLFWMouseButtonCallback mouseCallback;
    GLFWFramebufferSizeCallback fbCallback;
    GLFWCursorPosCallback cursorPosCallback;
    List<Triangle> allTriangles;
    long window;
    int width;
    int height;

    Matrix4f projMatrix = new Matrix4f();
    Matrix4f viewMatrix = new Matrix4f();

    FloatBuffer fb = BufferUtils.createFloatBuffer(16);

    void run() {

        try {
            init();
            loop();

            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            glfwTerminate();
            errorCallback.release();
        }
    }

    void init() {

        allTriangles=AllTriangles.findAllTriangles(50);
        triangleSize = allTriangles.size();
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);


        int WIDTH = 300;
        int HEIGHT = 300;

        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key,
                               int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, GL_TRUE);
            }
        });
        glfwSetInputMode(window,GLFW_CURSOR,GLFW_CURSOR_NORMAL);
        mouseX = mouseY = mouseDX = mouseDY = 0;
        glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long l, double xpos, double ypos) {
                if(mouseDown)
                {
                    System.out.println(xpos + "!!!" + ypos+"___"+mouseDX+" "+mouseDY);
                    if(mouseX!=0 && mouseY!=0)
                    {
                        mouseDX += (int) xpos - mouseX;
                        mouseDY += (int) ypos - mouseY;
                    }
                    mouseX = (int) xpos;
                    mouseY = (int) ypos;
                }
            }
        } );
        glfwSetMouseButtonCallback(window, mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int button, int action, int mods) {
                int x = 0,y = 0;
                if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS)
                {
                    mouseDown = true;
                    mouseDY = yAngle ;
                    mouseDX = xAngle;
                }//glfwGetCursorPos(window, x, y);
                if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_RELEASE)
                {
                    mouseDown = false;
                    mouseX = mouseY = mouseDX = mouseDY = 0;
                }
            }
        });
        glfwSetFramebufferSizeCallback(window,
                fbCallback = new GLFWFramebufferSizeCallback() {
                    public void invoke(long window, int w, int h) {
                        if (w > 0 && h > 0) {
                            width = w;
                            height = h;
                        }
                    }
                });
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);
    }

    void renderGraph() {
//        glBegin(GL_LINES);
//        glVertex3f(0.0f, 0.0f, 0.0f);
//        glVertex3f(1.0f, 0.0f, 0.0f);
//        glEnd();
//        glBegin(GL_LINES);
//        glVertex3f(0.0f, 0.0f, 0.0f);
//        glVertex3f(0.0f, -(float) Math.cos(yAngle), (float) Math.sin(yAngle));
//        glEnd();
//        glBegin(GL_QUADS);
//        glColor3f(0.0f, 0.0f, 0.2f);
//        glVertex3f(  0.5f, -0.5f, -0.5f );
//        glVertex3f( -0.5f, -0.5f, -0.5f );
//        glVertex3f( -0.5f,  0.5f, -0.5f );
//        glVertex3f(  0.5f,  0.5f, -0.5f );
//        glColor3f(   0.0f,  0.0f,  1.0f );
//        glVertex3f(  0.5f, -0.5f,  0.5f );
//        glVertex3f(  0.5f,  0.5f,  0.5f );
//        glVertex3f( -0.5f,  0.5f,  0.5f );
//        glVertex3f( -0.5f, -0.5f,  0.5f );
//        glColor3f(   1.0f,  0.0f,  0.0f );
//        glVertex3f(  0.5f, -0.5f, -0.5f );
//        glVertex3f(  0.5f,  0.5f, -0.5f );
//        glVertex3f(  0.5f,  0.5f,  0.5f );
//        glVertex3f(0.5f, -0.5f, 0.5f);
//        glColor3f(   0.2f,  0.0f,  0.0f );
//        glVertex3f( -0.5f, -0.5f,  0.5f );
//        glVertex3f(-0.5f, 0.5f, 0.5f);
//        glVertex3f(-0.5f, 0.5f, -0.5f);
//        glVertex3f(-0.5f, -0.5f, -0.5f);
//        glColor3f(   0.0f,  1.0f,  0.0f );
//        glVertex3f(  0.5f,  0.5f,  0.5f );
//        glVertex3f(  0.5f,  0.5f, -0.5f );
//        glVertex3f( -0.5f,  0.5f, -0.5f );
//        glVertex3f(-0.5f, 0.5f, 0.5f);
//        glColor3f(   0.0f,  0.2f,  0.0f );
//        glVertex3f(  0.5f, -0.5f, -0.5f );
//        glVertex3f(0.5f, -0.5f, 0.5f);
//        glVertex3f(-0.5f, -0.5f, 0.5f);
//        glVertex3f(-0.5f, -0.5f, -0.5f);
//        glEnd();
        glBegin(GL_LINE_LOOP);

        for (int i = 0; i < 500; i++)
        {
            glColor3f(   i/500f,  0.0f,  1f-i/500f );
            float angle = 2 * 3.1415927f * i / 500;
            glVertex2d(2*Math.cos(angle), Math.sin(angle));
        }
        glEnd();
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < 500; i++)
        {
            glColor3f(   i/500f,  1f-i/500f,  0.0f );
            float angle = 2 * 3.1415927f * i / 500;
            glVertex3d(0, Math.cos(angle), Math.sin(angle));
        }
        glEnd();
        glBegin(GL_LINE_LOOP);

        for (int i = 0; i < 500; i++)
        {
            glColor3f(   0.0f,  1f-i/500f,  i/500f );
            float angle = 2 * 3.1415927f * i / 500;
            glVertex3d( 2*Math.cos(angle),0, Math.sin(angle));
        }
        glEnd();
    }

    void renderTriangle(Triangle triangle,int a)
    {
        glBegin(GL_TRIANGLES);
        //glColor3f((float)a/(float)triangleSize, (float)a/(float)triangleSize, 1 - (float)a/(float)triangleSize);

        if(a%2 ==0)
        {
            glColor3f((float)a/(float)triangleSize, (float)a/(float)triangleSize, 1 - (float)a/(float)triangleSize);
        }
        else
        {
            glColor3f(1-(float)a/(float)triangleSize, (float)a/(float)triangleSize, (float)a/(float)triangleSize);
        }
        //Random random = new Random();
        //glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
//        if(a%2 ==0)
//        {
//            glColor3f(1, 1, 0);
//        }
//        else
//        {
//            glColor3f(1, 0, 1);
//        }
        glVertex3f(  (float)triangle.p0.x, (float)triangle.p0.y, (float)triangle.p0.z );
        glVertex3f(  (float)triangle.p1.x, (float)triangle.p1.y, (float)triangle.p1.z );
        glVertex3f(  (float)triangle.p2.x, (float)triangle.p2.y, (float)triangle.p2.z );
        glEnd();

    }

    void loop() {
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        long firstTime = System.nanoTime();

        while (glfwWindowShouldClose(window) == GL_FALSE) {
            long thisTime = System.nanoTime();
            float diff = (thisTime - firstTime) / 9E9f;
            glViewport(0, 0, width, height);
            projMatrix.setPerspective(45.0f, (float)width/height,
                    0.01f, 100.0f).get(fb);
            glMatrixMode(GL_PROJECTION);
            glLoadMatrixf(fb);
            Matrix4f matrix4f = viewMatrix.setLookAt(0.0f, 0.0f, 3.0f,
                    0.0f, 0.0f, 0.0f,
                    0.0f, 1.0f, 0.0f);
            if (mouseDown)
            {
                yAngle = mouseDY;
                xAngle = mouseDX;
            }
            matrix4f.rotate((float) (yAngle/1000.0), 1.0f, 0.0f, 0.0f).get(fb);
            matrix4f.rotate((float) (xAngle/1000.0), 0.0f, 1.0f, 0.0f).get(fb);
            glMatrixMode(GL_MODELVIEW);
            glLoadMatrixf(fb);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            renderGraph();
            int a = 0;
            for(Triangle triangle : allTriangles)
            {
                renderTriangle((Triangle) triangle,a);
                a++;
            }

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {

        new GraphDrawer().run();
    }
}