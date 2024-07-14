%%
%
%Project Name: Degree Distribution of Directed Signed Network
%Author: Dewan Ferdous Wahid
%Affiliation: Dr. Yong Gao's Research Group, Computer Science, UBC-Okanagan
%Date: March 10, 2016
%
%Dependency: 'powerlaw package' - written by Aaron Clauset, Cosma R. Shalizi and M.E.J. Newman
%Available in: http://tuvalu.santafe.edu/~aaronc/powerlaws/
%%.........................................................................

%Read the network data(.csv)
%filename = 'soc-sign-epinions-matlab.csv';
filename = 'model-a-v-10000-k-4.csv';
gData = csvread(filename);

%Separating directed edges into positive and negative edges 
n = size(gData);          % #of rows in network

positiveEdgeSets = zeros(1,3);
negativeEdgeSets = zeros(1,3);

for i = 1:n
    if gData(i,3) == 1     %if the edge is positive
        positiveEdgeSets = [positiveEdgeSets; gData(i,:)];
    elseif gData(i,3) == -1 %if the edge is negative
        negativeEdgeSets = [negativeEdgeSets; gData(i,:)];
    end
end

%Edge sets (positive and negative): Removing the first zeros row
positiveEdgeSets = positiveEdgeSets(2:end,:);
negativeEdgeSets = negativeEdgeSets(2:end,:);

%Split the directed positive-edge list into 'source' and 'target' 
positiveEdgeSources_all = positiveEdgeSets(:,1);
positiveEgdeTargets_all = positiveEdgeSets(:,2);

%Remove dublicated 'source' and 'target' in positive-edge list
positiveEdgeSources = unique(positiveEdgeSources_all);
positiveEgdeTargets = unique(positiveEgdeTargets_all);

%Split the directed negative edge list into 'source' and 'target'
negativeEdgeSources_all = negativeEdgeSets(:,1);
negativeEdgeTargets_all = negativeEdgeSets(:,2);

%Remove dublicated 'source' and 'target' 
negativeEdgeSources = unique(negativeEdgeSources_all);
negativeEdgeTargets = unique(negativeEdgeTargets_all);

%Positive-OUT-degree distributions
positiveOUTdegCount = histc(positiveEdgeSets(:,1), positiveEdgeSources);
positiveOUTdegDist = histc(positiveOUTdegCount, unique(positiveOUTdegCount));

%Positive-IN-degree distribution
positiveINdegCount = histc(positiveEdgeSets(:,2), positiveEgdeTargets);
positiveINdegDist = histc(positiveINdegCount, unique(positiveINdegCount));

%Negative-OUT-degree distributions
negativeOUTdegCount = histc(negativeEdgeSets(:,1), negativeEdgeSources);
negativeOUTdegDist = histc(negativeOUTdegCount, unique(negativeOUTdegCount));

%Negative-IN-degree distributions
negativeINdegCount = histc(negativeEdgeSets(:,2), negativeEdgeTargets);
negativeINdegDist = histc(negativeINdegCount, unique(negativeINdegCount));

%Positive-OUT-Degree distribution: Calutaing power-law parameters and
%fitted line
[alpha_pOut, dMin_pOut, L_pOut]=plfit(positiveOUTdegCount);
h_pOut = plplot(positiveOUTdegCount, dMin_pOut, alpha_pOut, 1);
[p_pOut,gof_pOut] = plpva(positiveOUTdegCount, dMin_pOut);

%Positive-IN-Degree distribution: Calculating power-law parameters and
%fitted line
[alpha_pIn, dMin_pIn, L_pIn]=plfit(positiveINdegCount);
h_pIn = plplot(positiveINdegCount, dMin_pIn, alpha_pIn, 2);
[p_pIn, gof_pIn]=plpva(positiveINdegCount, dMin_pIn);

%Negative-OUT-Degree distribution: Calutaing power-law parameters and
%fitted line
[alpha_nOut, dMin_nOut, L_nOut]=plfit(negativeOUTdegCount);
h_nOut = plplot(negativeOUTdegCount, dMin_nOut, alpha_nOut, 3);
[p_nOut,gof_nOut] = plpva(negativeOUTdegCount, dMin_nOut);

%Negative-IN-Degree distribution: Calculating power-law parameters and
%fitted line
[alpha_nIn, dMin_nIn, L_nIn]=plfit(negativeINdegCount);
h_nIn = plplot(negativeINdegCount, dMin_nIn, alpha_nIn, 4);
[p_nIn, gof_nIn]=plpva(negativeINdegCount, dMin_nIn);

%Distribution plots
figure;
scatter(1:length(positiveOUTdegDist), positiveOUTdegDist, 'g+'); hold on;
scatter(1:length(positiveINdegDist), positiveINdegDist, 'ro', 'filled'); hold on;
scatter(1:length(negativeOUTdegDist), negativeOUTdegDist, 'b*'); hold on;
scatter(1:length(negativeINdegDist), negativeINdegDist, 'co', 'filled'); 
legend('Positive-OUT-Degree', 'Positive-IN-Degree', 'Negative-OUT-Degree', 'Negative-IN-Degree');
set(gca,'xscale', 'log', 'yscale', 'log');
title('Model B: Edge copying model');
xlabel('degree d');
ylabel('# of vertices with degree d')
