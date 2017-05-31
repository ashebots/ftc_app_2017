package org.firstinspires.ftc.teamcode.teleop;

/**
 * Created by jezebelquit on 5/30/17.
 */

public class Toggle {
    boolean toggleGate = true;
    boolean toggleStatus = false;
    public boolean toggle(boolean toggleInput){
        if (toggleInput && toggleGate){
            toggleGate = false;
            toggleStatus = !toggleStatus;
        }
        if (!toggleInput && !toggleGate){
            toggleGate = true;
        }
        return toggleStatus;
    }
}
