package snake;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.canvas.*;
import javafx.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.util.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.image.Image;
import javafx.scene.media.*;
// Demonstrates switching between scenes at runtime
// Click on the button to switch scenes, OR press keys 1, 2

public class Main extends Application {
    Scene splash, level1, level2, level3, highscore, gOver;
    Label snake, name, userid, instructions, description, press, keys, hscore, gHscore, gScore,
            fruitEaten = new Label(), fruitEaten1 = new Label(), fruitEaten2 = new Label(), Score1 = new Label(),
            Score2 = new Label(), score = new Label();
    int HighScore = 0, Score = 0, fruit1 = 0, fruit2 = 0, fruit3 = 0, currentLevel = 0, posX = 1, posY = 0, negX = 0,
            negY = 0, gameOver = 0, pause = 0, food3Counter = 0;
    Stage primaryStage;
    Snake mainSnake;
    Group board, board2, board3;
    AnimationTimer animationTimer;
    Timeline animation;
    Rectangle mushroom = new Rectangle();
    ArrayList<foodCoordinates> food1 = new ArrayList<>(List.of(new foodCoordinates(15, 4), new foodCoordinates(8, 9),
            new foodCoordinates(3, 4), new foodCoordinates(6, 12), new foodCoordinates(19, 11)));
    ArrayList<foodCoordinates> food2 = new ArrayList<>(List.of(new foodCoordinates(11, 6), new foodCoordinates(3, 4),
            new foodCoordinates(12, 8), new foodCoordinates(8, 9), new foodCoordinates(13, 7),
            new foodCoordinates(16, 6), new foodCoordinates(15, 10), new foodCoordinates(3, 11),
            new foodCoordinates(2, 2), new foodCoordinates(19, 11)));
    ArrayList<foodCoordinates> food3 = new ArrayList<>(
            List.of(new foodCoordinates(7, 7), new foodCoordinates(18, 1), new foodCoordinates(5, 10),
                    new foodCoordinates(10, 10), new foodCoordinates(20, 12), new foodCoordinates(1, 1),
                    new foodCoordinates(5, 2), new foodCoordinates(14, 12), new foodCoordinates(14, 5),
                    new foodCoordinates(12, 10), new foodCoordinates(4, 4), new foodCoordinates(1, 12),
                    new foodCoordinates(20, 4), new foodCoordinates(9, 9), new foodCoordinates(20, 1)));

    enum SCENES {
        SPLASH, LEVEL1, LEVEL2, LEVEL3, HIGHSCORE, GAMEOVER
    };

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Snake Game");
        // splash scene
        snake = new Label("The Snake Game");
        snake.setTextFill(Color.LIGHTGREEN);
        snake.setFont(new Font("Verdana", 40));
        name = new Label("Name: Shruti Vishrant");
        name.setTextFill(Color.CYAN);
        name.setFont(new Font("Tahoma", 32));
        userid = new Label("userid: svishran");
        userid.setTextFill(Color.CYAN);
        userid.setFont(new Font("Tahoma", 32));
        instructions = new Label("How to Play:");
        instructions.setTextFill(Color.YELLOW);
        instructions.setFont(new Font("Tahoma", 30));
        description = new Label(
                "The snake will keep moving forward. Use Left and Right arrow keys to change direction. Help the snake eat the mushrooms and survive till the timer runs out in Level 1 and Level 2. In Level 3, eat until you die and lose. You lose if the snake's head hits the wall or if the snake eats itself.");
        description.setWrapText(true);
        description.setTextFill(Color.YELLOW);
        description.setFont(new Font("Tahoma", 30));
        press = new Label("Press:");
        press.setTextFill(Color.PINK);
        press.setFont(new Font("Tahoma", 30));
        keys = new Label("1: Start Level 1, 2: Start Level 2, 3: Start Level 3, Q: Quit");
        keys.setTextFill(Color.PINK);
        keys.setFont(new Font("Tahoma", 30));
        keys.setWrapText(true);
        VBox root1 = new VBox();
        root1.setSpacing(50);
        root1.setPadding(new Insets(0, 160, 0, 160));
        root1.setAlignment(Pos.CENTER);
        root1.getChildren().add(snake);
        root1.getChildren().add(name);
        root1.getChildren().add(userid);
        root1.getChildren().add(instructions);
        root1.getChildren().add(description);
        root1.getChildren().add(press);
        root1.getChildren().add(keys);
        root1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        splash = new Scene(root1, 1280, 800);
        splash.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DIGIT1) {
                setScene(primaryStage, SCENES.LEVEL1);
            } else if (event.getCode() == KeyCode.Q) {
                setScene(primaryStage, SCENES.HIGHSCORE);
            } else if (event.getCode() == KeyCode.DIGIT3) {
                setScene(primaryStage, SCENES.LEVEL3);
            } else if (event.getCode() == KeyCode.DIGIT2) {
                setScene(primaryStage, SCENES.LEVEL2);
            }
        });

        Label gameover = new Label("GAME OVER");
        gameover.setTextFill(Color.RED);
        gameover.setFont(new Font("Tahoma", 100));
        gHscore = new Label("HighScore: " + HighScore);
        gHscore.setTextFill(Color.ORANGE);
        gHscore.setFont(new Font("Tahoma", 50));
        gScore = new Label("Your Score: " + Score);
        gScore.setTextFill(Color.ORANGE);
        gScore.setFont(new Font("Tahoma", 50));
        Label press2 = new Label("Press:");
        press2.setTextFill(Color.PINK);
        press2.setFont(new Font("Tahoma", 30));
        Label keys2 = new Label("R: Restart Game, Q: Quit");
        keys2.setTextFill(Color.PINK);
        keys2.setFont(new Font("Tahoma", 30));
        keys2.setWrapText(true);
        VBox root6 = new VBox();
        root6.setSpacing(50);
        root6.setPadding(new Insets(0, 160, 0, 160));
        root6.setAlignment(Pos.CENTER);
        root6.getChildren().add(gameover);
        root6.getChildren().add(gHscore);
        root6.getChildren().add(gScore);
        root6.getChildren().add(press2);
        root6.getChildren().add(keys2);
        root6.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gOver = new Scene(root6, 1280, 800);
        gOver.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                Score = 0;
                pause = 0;
                gameOver = 0;
                currentLevel = 0;
                posX = 1;
                posY = 0;
                negX = 0;
                negY = 0;
                setScene(primaryStage, SCENES.SPLASH);
            } else if (event.getCode() == KeyCode.Q) {
                setScene(primaryStage, SCENES.HIGHSCORE);
            }
        });

        // HighScoreScene
        hscore = new Label("HighScore: " + HighScore);
        hscore.setTextFill(Color.ORANGE);
        hscore.setFont(new Font("Tahoma", 50));
        Label press3 = new Label("Press R to Start the Game");
        press3.setTextFill(Color.PINK);
        press3.setFont(new Font("Tahoma", 30));
        press3.setWrapText(true);
        VBox root5 = new VBox();
        root5.setSpacing(50);
        root5.setPadding(new Insets(0, 160, 0, 160));
        root5.setAlignment(Pos.CENTER);
        root5.getChildren().add(hscore);
        root5.getChildren().add(press3);
        root5.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        highscore = new Scene(root5, 1280, 800);
        highscore.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.R) {
                if (currentLevel != 0) {
                    animation.stop();
                    animationTimer.stop();
                }
                Score = 0;
                pause = 0;
                gameOver = 0;
                currentLevel = 0;
                posX = 1;
                posY = 0;
                negX = 0;
                negY = 0;
                setScene(primaryStage, SCENES.SPLASH);
            }
        });

        // show starting scene
        setScene(primaryStage, SCENES.SPLASH);
        primaryStage.show();
    }

    void setScene(Stage stage, SCENES scene) {
        switch (scene) {
            case SPLASH:
                stage.setScene(splash);
                break;
            case LEVEL1:
                // scene two
                if (currentLevel == 0) {
                    mainSnake = new Snake();
                }
                currentLevel = 1;
                fruit1 = 0;
                VBox root2 = createBoard(Score, 1, fruit1, fruitEaten1, Score1);
                final Canvas canvas = new Canvas(1280, 715);
                board = new Group();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setLineWidth(5);
                gc.strokeRect(10, 10, 1260, 702);
                createCheckBoard(gc);
                board.getChildren().add(canvas);
                board.getChildren().add(mushroom);
                drawFruit();
                mainSnake.drawSnake(board);
                root2.getChildren().add(board);
                root2.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
                level1 = new Scene(root2, 1280, 800);
                level1.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.R) {
                        Score = 0;
                        pause = 0;
                        gameOver = 0;
                        currentLevel = 0;
                        posX = 1;
                        posY = 0;
                        negX = 0;
                        negY = 0;
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.SPLASH);
                    } else if (event.getCode() == KeyCode.Q) {
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.HIGHSCORE);
                    } else if (event.getCode() == KeyCode.DIGIT3) {
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.LEVEL3);
                    } else if (event.getCode() == KeyCode.DIGIT2) {
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.LEVEL2);
                    } else if (event.getCode() == KeyCode.LEFT) {
                        if (posX == 1) {
                            mainSnake.head.setRotate(270);
                            posY = 1;
                            posX = 0;
                        } else if (posY == 1) {
                            mainSnake.head.setRotate(180);
                            negX = 1;
                            posY = 0;
                        } else if (negX == 1) {
                            mainSnake.head.setRotate(90);
                            negY = 1;
                            negX = 0;
                        } else if (negY == 1) {
                            mainSnake.head.setRotate(360);
                            posX = 1;
                            negY = 0;
                        }
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        if (posX == 1) {
                            mainSnake.head.setRotate(90);
                            negY = 1;
                            posX = 0;
                        } else if (posY == 1) {
                            mainSnake.head.setRotate(360);
                            posX = 1;
                            posY = 0;
                        } else if (negX == 1) {
                            mainSnake.head.setRotate(270);
                            posY = 1;
                            negX = 0;
                        } else if (negY == 1) {
                            mainSnake.head.setRotate(180);
                            negX = 1;
                            negY = 0;
                        }
                    } else if (event.getCode() == KeyCode.P) {
                        if (pause == 0) {
                            pause = 1;
                            animationTimer.stop();
                            animation.pause();
                        } else {
                            pause = 0;
                            animationTimer.start();
                            animation.play();
                        }
                    }
                });
                stage.setScene(level1);
                moveSnake();
                if (pause == 1) {
                    animationTimer.stop();
                    animation.pause();
                }
                break;
            case LEVEL2:
                fruit2 = 0;
                if (currentLevel == 0) {
                    mainSnake = new Snake();
                }
                currentLevel = 2;
                VBox root3 = createBoard(Score, 2, fruit2, fruitEaten2, Score2);
                final Canvas canvas2 = new Canvas(1280, 715);
                board2 = new Group();
                GraphicsContext gc2 = canvas2.getGraphicsContext2D();
                gc2.setLineWidth(5);
                gc2.strokeRect(10, 10, 1260, 702);
                createCheckBoard(gc2);
                board2.getChildren().add(canvas2);
                board2.getChildren().add(mushroom);
                drawFruit();
                mainSnake.drawSnake(board2);
                root3.getChildren().add(board2);
                root3.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
                level2 = new Scene(root3, 1280, 800);
                level2.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.R) {
                        Score = 0;
                        pause = 0;
                        gameOver = 0;
                        currentLevel = 0;
                        posX = 1;
                        posY = 0;
                        negX = 0;
                        negY = 0;
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.SPLASH);
                    } else if (event.getCode() == KeyCode.Q) {
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.HIGHSCORE);
                    } else if (event.getCode() == KeyCode.DIGIT1) {
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.LEVEL1);
                    } else if (event.getCode() == KeyCode.DIGIT3) {
                        animationTimer.stop();
                        animation.stop();
                        setScene(stage, SCENES.LEVEL3);
                    } else if (event.getCode() == KeyCode.LEFT) {
                        if (posX == 1) {
                            mainSnake.head.setRotate(270);
                            posY = 1;
                            posX = 0;
                        } else if (posY == 1) {
                            mainSnake.head.setRotate(180);
                            negX = 1;
                            posY = 0;
                        } else if (negX == 1) {
                            mainSnake.head.setRotate(90);
                            negY = 1;
                            negX = 0;
                        } else if (negY == 1) {
                            mainSnake.head.setRotate(360);
                            posX = 1;
                            negY = 0;
                        }
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        if (posX == 1) {
                            mainSnake.head.setRotate(90);
                            negY = 1;
                            posX = 0;
                        } else if (posY == 1) {
                            mainSnake.head.setRotate(360);
                            posX = 1;
                            posY = 0;
                        } else if (negX == 1) {
                            mainSnake.head.setRotate(270);
                            posY = 1;
                            negX = 0;
                        } else if (negY == 1) {
                            mainSnake.head.setRotate(180);
                            negX = 1;
                            negY = 0;
                        }
                    } else if (event.getCode() == KeyCode.P) {
                        if (pause == 0) {
                            pause = 1;
                            animationTimer.stop();
                            animation.pause();
                        } else {
                            pause = 0;
                            animationTimer.start();
                            animation.play();
                        }
                    }
                });
                stage.setScene(level2);
                moveSnake();
                if (pause == 1) {
                    animationTimer.stop();
                    animation.pause();
                }
                break;
            case LEVEL3:
                fruit3 = 0;
                food3Counter = 0;
                if (currentLevel == 0) {
                    mainSnake = new Snake();
                }
                currentLevel = 3;
                VBox root4 = new VBox();
                HBox panel = new HBox();
                panel.setSpacing(350);
                panel.setPadding(new Insets(20, 30, 20, 30));
                Label Level = new Label("Level " + 3);
                score = new Label("Score: " + Score);
                fruitEaten = new Label("Fruits Eaten: " + fruit3);
                Level.setTextFill(Color.YELLOW);
                Level.setFont(new Font("Verdana", 30));
                score.setTextFill(Color.YELLOW);
                score.setFont(new Font("Verdana", 30));
                fruitEaten.setTextFill(Color.YELLOW);
                fruitEaten.setFont(new Font("Verdana", 30));
                panel.getChildren().add(Level);
                panel.getChildren().add(score);
                panel.getChildren().add(fruitEaten);
                panel.setBackground(
                        new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                root4.getChildren().add(panel);
                final Canvas canvas3 = new Canvas(1280, 715);
                board3 = new Group();
                GraphicsContext gc3 = canvas3.getGraphicsContext2D();
                gc3.setLineWidth(5);
                gc3.strokeRect(10, 10, 1260, 702);
                createCheckBoard(gc3);
                board3.getChildren().add(canvas3);
                if (currentLevel == 0) {
                    mainSnake = new Snake();
                }
                board3.getChildren().add(mushroom);
                drawFruit();
                mainSnake.drawSnake(board3);
                root4.getChildren().add(board3);
                root4.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
                level3 = new Scene(root4, 1280, 800);
                level3.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.R) {
                        Score = 0;
                        pause = 0;
                        gameOver = 0;
                        currentLevel = 0;
                        posX = 1;
                        posY = 0;
                        negX = 0;
                        negY = 0;
                        animationTimer.stop();
                        setScene(primaryStage, SCENES.SPLASH);
                    } else if (event.getCode() == KeyCode.Q) {
                        animationTimer.stop();
                        setScene(primaryStage, SCENES.HIGHSCORE);
                    } else if (event.getCode() == KeyCode.DIGIT1) {
                        animationTimer.stop();
                        setScene(primaryStage, SCENES.LEVEL1);
                    } else if (event.getCode() == KeyCode.DIGIT2) {
                        animationTimer.stop();
                        setScene(primaryStage, SCENES.LEVEL2);
                    } else if (event.getCode() == KeyCode.LEFT) {
                        if (posX == 1) {
                            mainSnake.head.setRotate(270);
                            posY = 1;
                            posX = 0;
                        } else if (posY == 1) {
                            mainSnake.head.setRotate(180);
                            negX = 1;
                            posY = 0;
                        } else if (negX == 1) {
                            mainSnake.head.setRotate(90);
                            negY = 1;
                            negX = 0;
                        } else if (negY == 1) {
                            mainSnake.head.setRotate(360);
                            posX = 1;
                            negY = 0;
                        }
                    } else if (event.getCode() == KeyCode.RIGHT) {
                        if (posX == 1) {
                            mainSnake.head.setRotate(90);
                            negY = 1;
                            posX = 0;
                        } else if (posY == 1) {
                            mainSnake.head.setRotate(360);
                            posX = 1;
                            posY = 0;
                        } else if (negX == 1) {
                            mainSnake.head.setRotate(270);
                            posY = 1;
                            negX = 0;
                        } else if (negY == 1) {
                            mainSnake.head.setRotate(180);
                            negX = 1;
                            negY = 0;
                        }
                    } else if (event.getCode() == KeyCode.P) {
                        if (pause == 0) {
                            pause = 1;
                            animationTimer.stop();
                        } else {
                            pause = 0;
                            animationTimer.start();
                        }
                    }
                });
                stage.setScene(level3);
                moveSnake();
                if (pause == 1) {
                    animationTimer.stop();
                }
                break;
            case HIGHSCORE:
                stage.setScene(highscore);
                break;
            case GAMEOVER:
                if (currentLevel == 1 || currentLevel == 2) {
                    animation.stop();
                }
                animationTimer.stop();
                if (Score > HighScore) {
                    HighScore = Score;
                    gHscore.setText("HighScore: " + HighScore);
                    hscore.setText("HighScore: " + HighScore);
                }
                gScore.setText("Your Score: " + Score);
                stage.setScene(gOver);
                String sound = getClass().getClassLoader().getResource("gOver.mp3").toString();
                AudioClip clip = new AudioClip(sound);
                clip.play();

                break;
        }
    }

    VBox createBoard(int points, int level, int fruit, Label mushroomEaten, Label cScore) {
        VBox vbox = new VBox();
        HBox panel = new HBox();
        panel.setSpacing(160);
        panel.setPadding(new Insets(20, 30, 20, 30));
        Label Level = new Label("Level " + level);
        cScore.setText("Score: " + points);
        mushroomEaten.setText("Fruits Eaten: " + fruit);
        countDown timer = new countDown();
        Level.setTextFill(Color.YELLOW);
        Level.setFont(new Font("Verdana", 30));
        cScore.setTextFill(Color.YELLOW);
        cScore.setFont(new Font("Verdana", 30));
        mushroomEaten.setTextFill(Color.YELLOW);
        mushroomEaten.setFont(new Font("Verdana", 30));
        panel.getChildren().add(Level);
        panel.getChildren().add(cScore);
        panel.getChildren().add(mushroomEaten);
        panel.getChildren().add(timer);
        panel.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.getChildren().add(panel);
        return vbox;
    }

    public class countDown extends VBox {
        int timer = 30;
        int level;
        Label timeLeft = new Label("Time Left: 30");

        public countDown() {
            level = currentLevel;
            timeLeft.setTextFill(Color.YELLOW);
            timeLeft.setFont(new Font("Verdana", 30));
            getChildren().add(timeLeft);
            animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> countDownClock()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        }

        public void countDownClock() {
            if (timer != 0) {
                if (level == currentLevel) {
                    if (timer > 0) {
                        timer--;
                    }
                    timeLeft.setText("Time Left: " + timer);
                } else {
                    animation.stop();
                    animationTimer.stop();
                }
            } else {
                if (gameOver == 0) {
                    if (currentLevel == 1) {
                        animation.stop();
                        animationTimer.stop();
                        setScene(primaryStage, SCENES.LEVEL2);
                    } else if (currentLevel == 2) {
                        animation.stop();
                        animationTimer.stop();
                        setScene(primaryStage, SCENES.LEVEL3);
                    }
                } else {
                    animation.stop();
                    animationTimer.stop();
                }
            }
        }
    }

    public class foodCoordinates {
        double x;
        double y;

        public foodCoordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public void drawFruit() {
        Image mushroomPic = new Image("mushroom-2.png");
        mushroom.setFill(new ImagePattern(mushroomPic));
        mushroom.setWidth(62.7);
        mushroom.setHeight(58);
        if (currentLevel == 1) {
            mushroom.setX((food1.get(fruit1).x - 1) * 62.7 + 13);
            mushroom.setY((food1.get(fruit1).y - 1) * 58 + 13);

        } else if (currentLevel == 2) {
            mushroom.setX((food2.get(fruit2).x - 1) * 62.7 + 13);
            mushroom.setY((food2.get(fruit2).y - 1) * 58 + 13);
        } else if (currentLevel == 3) {
            mushroom.setX((food3.get(food3Counter).x - 1) * 62.7 + 13);
            mushroom.setY((food3.get(food3Counter).y - 1) * 58 + 13);
        }
    }

    public void moveSnake() {
        animationTimer = new AnimationTimer() {

            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (gameOver == 0) {
                    if (now - lastUpdate > 450000000 / currentLevel) {
                        tick();
                        lastUpdate = now;
                    }
                } else {
                    this.stop();
                }

            }
        };
        animationTimer.start();
    }

    public void tick() {
        for (int i = mainSnake.snakeBody.size() - 1; i >= 1; i--) {
            mainSnake.snakeBody.get(i).setX(mainSnake.snakeBody.get(i - 1).getX());
            mainSnake.snakeBody.get(i).setY(mainSnake.snakeBody.get(i - 1).getY());
        }
        mainSnake.snakeBody.get(0).setX(mainSnake.head.getX());
        mainSnake.snakeBody.get(0).setY(mainSnake.head.getY());
        if (posX == 1) {
            mainSnake.head.setX(mainSnake.head.getX() + 62.7);
            if (mainSnake.head.getX() >= 1254) {
                gameOver = 1;
                setScene(primaryStage, SCENES.GAMEOVER);
            }

        } else if (posY == 1) {
            mainSnake.head.setY(mainSnake.head.getY() - 58.0);
            if (mainSnake.head.getY() < 13) {
                gameOver = 1;
                setScene(primaryStage, SCENES.GAMEOVER);
            }
        } else if (negX == 1) {
            mainSnake.head.setX(mainSnake.head.getX() - 62.7);
            if (mainSnake.head.getX() < 0) {
                gameOver = 1;
                setScene(primaryStage, SCENES.GAMEOVER);
            }
        } else if (negY == 1) {
            mainSnake.head.setY(mainSnake.head.getY() + 58.0);
            if (mainSnake.head.getY() >= 696) {
                gameOver = 1;
                setScene(primaryStage, SCENES.GAMEOVER);
            }
        }

        int headX;
        if (mainSnake.head.getX() < 13) {
            headX = 13;
        } else {
            headX = (int) mainSnake.head.getX();
        }
        if ((int) mushroom.getX() == headX && (int) mushroom.getY() == (int) mainSnake.head.getY()) {
            String sound = getClass().getClassLoader().getResource("eatsound.mp3").toString();
            AudioClip clip = new AudioClip(sound);
            clip.play();

            Score = Score + 1;
            if (currentLevel == 1) {
                fruit1 = fruit1 + 1;
                Score1.setText("Score: " + Score);
                fruitEaten1.setText("Fruits Eaten: " + fruit1);
                if (fruit1 < 5) {
                    drawFruit();
                    mainSnake.growSnake(board);
                } else {
                    mushroom.setHeight(0);
                    mushroom.setWidth(0);
                    animation.stop();
                    animationTimer.stop();
                    setScene(primaryStage, SCENES.LEVEL2);
                }
            } else if (currentLevel == 2) {
                fruit2 = fruit2 + 1;
                Score2.setText("Score: " + Score);
                fruitEaten2.setText("Fruits Eaten: " + fruit2);
                if (fruit2 < 10) {
                    drawFruit();
                    mainSnake.growSnake(board2);
                } else {
                    mushroom.setHeight(0);
                    mushroom.setWidth(0);
                    animation.stop();
                    animationTimer.stop();
                    setScene(primaryStage, SCENES.LEVEL3);
                }
            } else if (currentLevel == 3) {
                fruit3 = fruit3 + 1;
                food3Counter = food3Counter + 1;
                score.setText("Score: " + Score);
                fruitEaten.setText("Fruits Eaten: " + fruit3);
                if (food3Counter < 15) {
                    drawFruit();
                    mainSnake.growSnake(board3);
                } else {
                    food3Counter = 0;
                    drawFruit();
                    mainSnake.growSnake(board3);
                }
            }
        }
        for (int i = 1; i < mainSnake.snakeBody.size(); i++) {
            if ((int) mainSnake.head.getX() == (int) mainSnake.snakeBody.get(i).getX()
                    && (int) mainSnake.head.getY() == mainSnake.snakeBody.get(i).getY()) {
                gameOver = 1;
                setScene(primaryStage, SCENES.GAMEOVER);
            }
        }
    }

    public class Snake {
        Rectangle head;
        ArrayList<Rectangle> snakeBody = new ArrayList<>();

        public Snake() {
            Image snakeHead = new Image("snakeHead.png");
            head = new Rectangle(138.4, 187, 62.7, 58);
            head.setArcHeight(100);
            head.setArcWidth(100);
            head.setFill(new ImagePattern(snakeHead));
            Rectangle r = new Rectangle(75.7, 187, 62.7, 58);
            r.setArcHeight(100);
            r.setArcWidth(100);
            r.setFill(Color.BLACK);
            Rectangle r2 = new Rectangle(13, 187, 62.7, 58);
            r2.setArcHeight(100);
            r2.setArcWidth(100);
            r2.setFill(Color.BLACK);
            snakeBody.add(r);
            snakeBody.add(r2);
        }

        public void drawSnake(Group g) {
            g.getChildren().add(head);
            for (Rectangle rectangle : snakeBody) {
                g.getChildren().add(rectangle);
            }
        }

        public void growSnake(Group g) {
            Rectangle newSegment;
            if (posX == 1) {
                newSegment = new Rectangle(snakeBody.get(snakeBody.size() - 1).getX() - 62.7,
                        snakeBody.get(snakeBody.size() - 1).getY(), 62.7, 58);
            } else if (posY == 1) {
                newSegment = new Rectangle(snakeBody.get(snakeBody.size() - 1).getX(),
                        snakeBody.get(snakeBody.size() - 1).getY() + 58, 62.7, 58);
            } else if (negY == 1) {
                newSegment = new Rectangle(snakeBody.get(snakeBody.size() - 1).getX(),
                        snakeBody.get(snakeBody.size() - 1).getY() - 58, 62.7, 58);
            } else {
                newSegment = new Rectangle(snakeBody.get(snakeBody.size() - 1).getX() + 62.7,
                        snakeBody.get(snakeBody.size() - 1).getY(), 62.7, 58);
            }
            newSegment.setArcWidth(100);
            newSegment.setArcHeight(100);
            newSegment.setFill(Color.BLACK);
            snakeBody.add(newSegment);
            g.getChildren().add(newSegment);
        }

    }

    void createCheckBoard(GraphicsContext g) {
        double h = 13;
        for (int i = 0; i < 12; i++) {
            double w = 13;
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    g.setFill(Color.valueOf("#00DB00"));
                    g.fillRect(w, h, 62.7, 58);
                    g.setFill(Color.valueOf("#39FF14"));
                    g.fillRect(w + 62.7, h, 62.7, 58);
                    w = w + 125.4;
                } else {
                    g.setFill(Color.valueOf("#39FF14"));
                    g.fillRect(w, h, 62.7, 58);
                    g.setFill(Color.valueOf("#00DB00"));
                    g.fillRect(w + 62.7, h, 62.7, 58);
                    w = w + 125.4;
                }
            }
            h = h + 58;
        }
    }
}
