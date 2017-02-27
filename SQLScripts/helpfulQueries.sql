DELETE FROM "Service";
DELETE FROM "Operation";
DELETE FROM "Element";

ALTER SEQUENCE "seqService" RESTART;
ALTER SEQUENCE "seqOperation" RESTART;
ALTER SEQUENCE "seqElement" RESTART;

SELECT * FROM "Service";
SELECT * FROM "Operation";
SELECT * FROM "Element";

INSERT INTO "Element" ("ElementID","OperationID","ElementName") VALUES (nextval('"seqElement"'),SELECT "OperationID" FROM "Operation" WHERE "MessageName" = 'exportAllRecordsRequest', 'exportAllRecords');	


select S."ServiceName",O."OperationName",O."MessageName",O."OperationType",E."ElementName" from "Element" AS E,"Operation" AS O, "Service" As S
Where S."ServiceID" = O."ServiceID"
And O."OperationID" = E."OperationID"
Order by S."ServiceName";

select S."ServiceName",O."OperationName",O."OperationType",E."ElementName" from "Element" AS E,"Operation" AS O, "Service" As S
Where S."ServiceID" = O."ServiceID"
And O."OperationID" = E."OperationID"
AND O."OperationType" = '0'
AND S."ServiceName" = 'ACHWorksWS';