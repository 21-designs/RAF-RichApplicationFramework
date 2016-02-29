/*
 * This file is part of the application library that simplifies common 
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.drost.application;

class ExceptionMessages 
{
	final static String MESSAGE_APPLICATION_INVALID_ID = "Invalid argument. The unique identifer for the application cannot be empty or null.";
	final static String MESSAGE_APPLICATION_NOT_INITIALIZED = "The application has not been launched. Cannot get an undefined instance.";
	final static String MESSAGE_APPLICATION_ALREADY_INITIALIZED = "The application has already been initialized.";
	
	final static String MESSAGE_VIEW_HAS_MAIN_VIEW = "View has already been initialized.";
}
