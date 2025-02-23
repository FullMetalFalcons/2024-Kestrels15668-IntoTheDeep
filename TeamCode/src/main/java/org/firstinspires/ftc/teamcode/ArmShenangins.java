package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous
public class ArmShenangins extends LinearOpMode {
    public void runOpMode() {
        //init
        KestrelArm kestrelArm = new KestrelArm(hardwareMap, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(34.6, 63.3, Math.toRadians(-90)));

        //stops claw from moving during init
        KestrelArm.Claw.setPosition(0.4);
        KestrelArm.ClawRotator.setPosition(0.03);
        sleep(400);

        //declare actions
        Action waitStuff = drive.actionBuilder(drive.pose)
                .waitSeconds(5)
                .build();

        Action armExtend = new KestrelArm.ArmRotatorToPosition(kestrelArm, 6000, 2.0); //extend arm, if you need to read this you're just plain out stupid and shouldn't be coding, ima be honest
        Action armRetract = new KestrelArm.ArmRotatorToPosition(kestrelArm, 0, 2.0);  //un-extend arm
        Action liftUp = new KestrelArm.LiftToPosition(kestrelArm, 4000, 2.0); // Move lift up
        Action slideExtend = new KestrelArm.SlideToPosition(kestrelArm, 5000, 2.0); // Extend slide
        Action liftDown = new KestrelArm.LiftToPosition(kestrelArm, 0, 2.0); // Lower lift
        Action slideRetract = new KestrelArm.SlideToPosition(kestrelArm, 0, 2.0); // Retract slide

        //ooiiioooiiiioiioioiooiiiiiioooo
        Action test = new SequentialAction(
                //armExtend,
                //waitStuff,
                //armRetract,
                //waitStuff,
                //liftUp,
                //waitStuff,
                //liftDown,
                slideExtend,
                waitStuff,
                slideRetract
        );

        waitForStart();
        if (isStopRequested()) return;

        // Run the complete autonomous sequence
        Actions.runBlocking(test);
    }
}
