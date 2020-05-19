/*******************************************************************************
 * Copyright (c) 2019 Advantest. All rights reserved.
 *
 * Contributors:
 *     Advantest - initial API and implementation
 *******************************************************************************/
package learnJob;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import job.LightOnJobWithNonRull;

/**
 * @author linzhang
 *
 */

public class LightSwitch {
    private boolean isOn = false;

    public boolean isOn() {
        return isOn;
    }

    public void on() {
        new LightOnJobWithNonRull().schedule();
    }

    public void off() {
        new LightOnJobWithNonRull().schedule();
    }

    public void up() {
        new LightUp().schedule();
    }

    class LightUp extends Job {
        public LightUp() {
            super("Turning up the light");
        }

        @Override
        public IStatus run(IProgressMonitor monitor) {
            ISchedulingRule privateRule = new OwnRule();
            System.out.println("Turning the light up");
            Job.getJobManager().beginRule(privateRule, null);
            System.out.println("Turning the light up-->beginrule");


            Job.getJobManager().endRule(privateRule);

            return Status.OK_STATUS;
        }
    }

    public class OwnRule implements ISchedulingRule {

        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule == OwnRule.this;
        }

        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule instanceof OwnRule;
        }
    }

    public static void main(String[] args) throws OperationCanceledException, InterruptedException {
        testThread();

        LightSwitch light = new LightSwitch();
        for (int i = 0; i < 100; i++) {
            light.on();
            light.up();
            Thread.sleep(3000);
            light.off();

            Job.getJobManager().join(null, null);

            Job[] jobs = Job.getJobManager().find(null);
            assert (jobs.length == 0);
            System.out.println("The light is on? " + light.isOn() + " count : " + i);
            if (light.isOn) {
                break;
            }
        }
    }

    /**
     *
     */
    private static void testThread() {
        Thread mainThread = Thread.currentThread();
        Map<Thread, StackTraceElement[]> threadToStackTraces = mainThread.getAllStackTraces();
        for (Entry<Thread, StackTraceElement[]> entry : threadToStackTraces.entrySet()) {
            Thread currentThread = entry.getKey();
            StackTraceElement[] stackTraceElements = entry.getValue();
            System.out.println("Thread: " + currentThread.getName());
        }
    }
}
