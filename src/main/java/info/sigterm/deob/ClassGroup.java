package info.sigterm.deob;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassGroup
{
	private ArrayList<ClassFile> classes = new ArrayList<ClassFile>();

	public ClassGroup()
	{
	}

	public ClassFile addClass(String name, DataInputStream is) throws IOException
	{
		ClassFile cf = new ClassFile(this, is);
		classes.add(cf);
		return cf;
	}
	
	public List<ClassFile> getClasses()
	{
		return classes;
	}

	public ClassFile findClass(String name)
	{
		// XXX handle arrays?
		for (ClassFile c : classes)
			if (c.getName().equals(name))
				return c;
		return null;
	}

	public void buildClassGraph()
	{
		for (ClassFile c : classes)
			c.buildClassGraph();
	}

	public void buildInstructionGraph()
	{
		for (ClassFile c : classes)
			c.buildInstructionGraph();
	}
	
	public void buildCallGraph()
	{
		for (ClassFile c : classes)
			for (Method m : c.getMethods().getMethods())
			{
				m.callsTo.clear();
				m.callsFrom.clear();
			}
		for (ClassFile c : classes)
			c.buildCallGraph();
	}
}
