/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.client.test;

import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.test.annotations.*;

import java.util.Date;

@Entity(name="TestReflection")
@Table(name="Table_Test")
public class TestReflection implements Reflection {
	private Date date;
	private String string;
	private boolean bool;
	
	@Id
  private String id;
	
	public TestReflection(){
		
	}
	
	@Id
  public String getId() {
    return id;
  }

  public void setId(String Id) {
    this.id = Id;
  }

	
	public boolean getBool() {
		return bool;
	}
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}

}
