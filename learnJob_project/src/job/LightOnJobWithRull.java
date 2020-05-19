package job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class LightOnJobWithRull extends Job {
        public LightOnJobWithRull() {
            super("The Light on job with rule.");
            setRule(new LightRule());
        }

        @Override
        public IStatus run(IProgressMonitor monitor) {
            System.out.println("Turning on the light with rule.");
            return Status.OK_STATUS;
        }
}
