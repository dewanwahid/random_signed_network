%
%Project Name: Degree Distribution of Directed Network
%Author: Dewan Ferdous Wahid
%Institution: Dr. Yong Gao's Research Group, Computer Science, UBC-Okanagan
%Date: March 10, 2016
%...........................................................................

%Read the network data(.csv)
filename = 'Wiki-Vote.csv';
gData = csvread(filename);

edgeSources_all = gData(:,1);
edgeTargets_all = gData(:,2);

edgeSources = unique(edgeSources_all);
edgeTargets = unique(edgeTargets_all);

%%1:length(outDegreeDist)
outDegreeCount = histc(gData(:,1), edgeSources);
inDegreeCount = histc(gData(:,2), edgeTargets);

outDegreeDist = histc(outDegreeCount, edgeSources);
inDegreeDist = histc (inDegreeCount, edgeTargets);

figure;
scatter(1:length(outDegreeDist), outDegreeDist, '*');
hold on;
scatter(1:length(inDegreeDist),inDegreeDist, 'o');
set(gca,'xscale', 'log', 'yscale', 'log');