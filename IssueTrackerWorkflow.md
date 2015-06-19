This page details the overall life cycle of the issue tracker as
currently being used.

# Issue tracker workflow #

## 1. User reports the issue ##

User reports the bug, with the summary , description, if possible
attachments (snapshots) and status set as 'New'.

## 2. Project manager check the issue ##

Project Manager check the bugs and tries to replicate it. If the bug is
present in the system, he set the status 'Accepted'. Sometimes, the staus
might be assgined as Invalid, Duplicate or Wontfix depending on the nature of
the issue.


### Status of an issue ###

**New                  = Issue has not had initial review yet** Accepted             = Problem reproduced / Need acknowledged

Started              = Work on this issue has begun

Fixed                = Developer made requested changes, QA should verify

Verified             = QA has verified that the fix worked

Invalid              = This was not a valid issue report

Duplicate            = This report duplicates an existing issue

WontFix              = We decided to not take action on this issue


## 3. Project manager assign issue to developer(s) ##

PM Assign these attributes for the issue (eg. Type, Priority, Milestone) and
assign the task to developers.


Milestone-Release0.1Alpha = Lab prototype; Minimum functionality working

Milestone-Release0.2Beta = RSS,6 modules, video help manual, new layout

Milestone-Release0.2.1Beta = show selected ontology, improved search and
video manuals, manage display languages, IE bugs fix

Milestone-Release0.3 = Revision of some modules, Additional functionalities
Statistics and preferences

Milestone-Release1.0 = All essential functionality working, WB interface in
multiple languages (EN, FR, ES)

Milestone-Release2.0 = New developments, Implementation with the Protégé APIv4


Type-Defect          = Report of a software defect

Type-Enhancement     = Request for enhancement

Type-Task            = Work item that doesn't change the code or docs

Type-Patch           = Source code patch for review

Type-Other           = Some other kind of issue



Priority-Critical    = Must resolve in the specified milestone

Priority-High        = Strongly want to resolve in the specified milestone

Priority-Medium      = Normal priority

Priority-Low         = Might slip to later milestone

OpSys-All            = Affects all operating systems

OpSys-Windows        = Affects Windows users

OpSys-Linux          = Affects Linux users

OpSys-OSX            = Affects Mac OS X users

Component-UI         = Issue relates to program UI

Component-Logic      = Issue relates to application logic

Component-Persistence = Issue relates to data storage components

Component-Scripts    = Utility and installation scripts

Component-Docs       = Issue relates to end-user documentation

Security             = Security risk to users

Performance          = Performance issue

Usability            = Affects program usability

Maintainability      = Hinders future changes

AssignedTo-Prashant  = Prashanta Man Shrestha

AssignedTo-Sachit    = Sachit Rajbhandari

AssignedTo-Gudrun    = Gudrun Johannsen

AssignedTo-Armando   = Armando Stellato


## 4. Developers  work on the issue ##

Developers can see the task assigned and see set the status 'Started'.

Once finished, the issue status can be set to 'Fixed' and post patch on SVN.

It is thus important that developers make it clear wherever the fix for the bug will be deployed (or at least they ask someone to do it), before they mark one bug as fixed.


## 5. Quality assurance (QA) over the fixed issues ##

Users (or, again, even developers who did not participate in fixing the bug, but evaluated the outcome from a user perspective) may act as QA.

QA check all the fixed issues, verifies them and marks them as 'Verified'. If QA finds the bug is fixed, marks it as 'Fixed', otherwise as 'New'.



## Example scenario ##

# USER1 posts an issue

# Issue is assigned to DEVELOPER1.

# DEVELOPER1 reads the issue, fixes it, replies to the issue, and put a patch as an attachment to the reply (or explain how to solve it or whatever mean is better for the specific case).

DEVELOPER1 doesn't mark the issue as fixed. She puts DEVELOPER2 in CC.

# DEVELOPER2 acknowledges the receipt of the patch, accepts it, and applies it to current "live code". He tells the fix will be active on release X.Y.Z and marks the issue as "fixed".

# Version X.Y.Z is officially deployed. USER2 opens the WB and checks behavior from her user perspective. She finds it ok. She reopens the issue in google code and marks it as "QA verified".



Add your content here.


# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages