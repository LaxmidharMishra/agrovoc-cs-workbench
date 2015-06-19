# Possible services built in top of the Concept Server #

## News via RSS ##

Develop a jsp/java page that connect via the protégé api to the repository and export the recent changes (based on date in input as parameters) in XML format Atom 1.0 or better in format rss version="2.0".

Parameters in input can be:
  1. "date from" which export news
  1. "date to" for exporting news (default = today())
  1. db to connect to (maybe the rice module1 or the rice module4, or the example file, etc.)
  1. lang: the language of the labels for terms or concept to export
  1. owner (export data of a specific owner (default=all) --> if applicable
  1. others if needed...

Example:

http://www.ku...../..../rss/rss_wb_results.jsp?user=sinim&status=published&datefrom=1/2/2008&dateto=31/12/2008&lang=en&db=rice3

or

http://www.ku...../..../rss/rss_wb_results.do?user=123&status=3&datefrom=1/2/2008&dateto=31/12/2008&lang=en&db=2

Deadline: end of May.

See also:
  * http://www.agrifeeds.org/en/node/1044/xml
  * http://www.fao.org/waicent/whats_new/help/help_en.htm
  * Example of FAO AIMS Rss info:   http://www.fao.org/nems/rss/rss_nems_results.asp?owner=615&status=10&dateto=31/12/2009&lang=en&sites=1

## Other services.... ##