#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

void activitySelection(vector<int> timeTaken, vector<int> deadline, int n) {
    vector<pair<int, pair<int, int>>> activities;
    for (int i = 0; i < n; i++) {
        activities.push_back({deadline[i], {timeTaken[i], i + 1}}); 
    }
    sort(activities.begin(), activities.end());

    vector<int> selectedActivities; 
    int lastEndTime = -1; 
    for (auto &activity : activities) {
        int currentStartTime = activity.second.first;
        int currentEndTime = activity.first;
        if (currentEndTime-currentStartTime >= lastEndTime) {
            selectedActivities.push_back(activity.second.second); 
            lastEndTime = currentEndTime;
        }
    }

    cout << "Total activities: " << selectedActivities.size() << endl;
    cout << "List of selected activities: ";
    for (int i = 0; i < selectedActivities.size(); i++) {
        cout << selectedActivities[i];
        if (i < selectedActivities.size() - 1) {
            cout << ", ";
        }
    }
    cout << endl;
}

int main() {
    int n;
    cout << "Enter total number of activities: ";
    cin >> n;

    vector<int> timeTaken(n);
    vector<int> deadline(n);
        cout << "Enter timeTaken times for each activity: ";
    for (int i = 0; i < n; i++) {
        cin >> timeTaken[i];
    }    
    cout << "Enter deadline times for each activity: ";
    for (int i = 0; i < n; i++) {
        cin >> deadline[i];
    }
    activitySelection(timeTaken, deadline, n);

    return 0;
}
